package com.citi_team_one.tps.model;

public class CusipUser {
    private Integer id;

    private Integer userId;

    private String productId;

    public CusipUser(Integer id, Integer userId, String productId) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
    }

    public CusipUser() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }
}