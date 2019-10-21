package com.citi_team_one.tps.model;

import java.util.Date;

public class Product {
    private Integer id;

    private String cusip;

    private Double faceValue;

    private Double coupon;

    private Date maturityDate;

    public Product(Integer id, String cusip, Double faceValue, Double coupon, Date maturityDate) {
        this.id = id;
        this.cusip = cusip;
        this.faceValue = faceValue;
        this.coupon = coupon;
        this.maturityDate = maturityDate;
    }

    public Product() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCusip() {
        return cusip;
    }

    public void setCusip(String cusip) {
        this.cusip = cusip == null ? null : cusip.trim();
    }

    public Double getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(Double faceValue) {
        this.faceValue = faceValue;
    }

    public Double getCoupon() {
        return coupon;
    }

    public void setCoupon(Double coupon) {
        this.coupon = coupon;
    }

    public Date getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(Date maturityDate) {
        this.maturityDate = maturityDate;
    }
}