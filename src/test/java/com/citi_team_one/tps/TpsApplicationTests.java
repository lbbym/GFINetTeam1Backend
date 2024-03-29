package com.citi_team_one.tps;

import com.citi_team_one.tps.model.ResponseBean;
import com.citi_team_one.tps.model.SalerDeal;
import com.citi_team_one.tps.model.TraderDeal;
import com.citi_team_one.tps.service.CusipUserService;
import com.citi_team_one.tps.service.SalerDealsService;
import com.citi_team_one.tps.service.TraderDealsService;
import com.citi_team_one.tps.service.UserService;
import com.citi_team_one.tps.utils.BOVerify;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TpsApplicationTests {
    @Autowired
    private TraderDealsService traderDealsService;
    @Autowired
    private SalerDealsService salerDealsService;
    @Autowired
    private UserService userService;
    @Autowired
    private CusipUserService cusipUserService;
    @Autowired
    private TestRestTemplate restTemplate;
    private final String baseUrl = "/api";

    @BeforeEach
    public void before() throws SQLException {
        String path = ClassLoader.getSystemResource("SetUp.sql").getPath();
        System.out.println("Set Up Test Data Base...");

        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://" + "127.0.0.1" + ":" + "3306" + "/" + "tps";
        Connection conn = null;

        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, "root", "PwdIs2019!");
            conn.setAutoCommit(false);

            ScriptRunner runner = new ScriptRunner(conn);
            runner.setAutoCommit(false);
            runner.setStopOnError(true);
            runner.setSendFullScript(false);
            runner.setDelimiter(";");
            runner.setFullLineDelimiter(false);
            runner.setLogWriter(null);
            runner.runScript(new InputStreamReader(new FileInputStream(path), "utf-8"));

            conn.commit();
        } catch (Exception e) {
            conn.rollback();
            e.printStackTrace();
        } finally {
            conn.close();
        }
    }

    @Test
    void login() {
        String url = baseUrl + "/login?name=test_user&pwd=111";
        ResponseEntity<ResponseBean> responseBeanResponseEntity = restTemplate.postForEntity(url, null, ResponseBean.class);
        assertEquals("id", 1, responseBeanResponseEntity.getBody().getResult().getInteger("userId"));
    }

    @Test
    void register() {
        String url = baseUrl + "/register?name=smith&pwd=111&roleId=1";
        restTemplate.postForEntity(url, null, Object.class);
        assertEquals("id", 2, userService.findByName("smith").getId());
    }

    @Test
    void addCusipUser() {
        String url = baseUrl + "/cusipUser?traderId=1&productId=1234";
        restTemplate.postForEntity(url, null, Object.class);
        assertEquals("id", 1.1, cusipUserService.findProductsByTraderId(1).get(0).getCoupon());
    }

    @Test
    void getCusips() {
        //    String url = baseUrl + "/cusipUser?traderId=1&productId=1234";
        //    restTemplate.getForEntity(url, null, Object.class);
        //    assertEquals("id", 1.1, cusipUserService.findProductsByTraderId(1).get(0).getCoupon());
    }

    @Test
    void testBO() {
        SalerDeal salerDeal = new SalerDeal();
        salerDeal.setTxnI(1);
        salerDeal.setPrice((double) 199);

        TraderDeal traderDeal = new TraderDeal();
        traderDeal.setTxnI(1);

        BOVerify BOVerify = new BOVerify();
        try {
            BOVerify.getBOVerifyMsg(salerDeal, traderDeal);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void contextLoads() {
        System.out.println("Context Loads...");
    }
}
