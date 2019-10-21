package com.citi_team_one.tps.model;

import com.alibaba.fastjson.JSONObject;

public class ResponseBean {

    // http 状态码
    private int code;

    // 返回信息
    private String msg;

    // 返回的数据
    private JSONObject result;

    public ResponseBean(int code, String msg, JSONObject result) {
        this.code = code;
        this.msg = msg;
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public JSONObject getResult() {
        return result;
    }

    public void setResult(JSONObject data) {
        this.result = result;
    }
}
