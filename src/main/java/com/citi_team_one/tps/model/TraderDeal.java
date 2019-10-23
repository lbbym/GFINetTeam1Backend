package com.citi_team_one.tps.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class TraderDeal implements Serializable {
    private Integer txnI;
    private String productId;
    private Integer volume;
    private Double price;
    private Double notionalPrincipal;
    private String orderId;
    private Integer tradeSender;
    private Integer tradeReciver;
    private Date timestamp;
    private String interOrigSys;
    private Integer interI;
    private Integer interVNum;
    private Integer ver;
    private String status;
    private Integer rejectCode;
    private String rejectReason;
    private String tradeOrigSys;
    public TraderDeal(Integer txnI, String productId, Integer volume,
                      Double price, Double notionalPrincipal, String orderId,
                      Integer tradeSender, Integer tradeReciver, Date timestamp,
                      String interOrigSys, Integer interI, Integer interVNum, Integer ver,
                      String status, Integer rejectCode, String rejectReason, String tradeOrigSys) {
        this.txnI = txnI;
        this.productId = productId;
        this.volume = volume;
        this.price = price;
        this.notionalPrincipal = notionalPrincipal;
        this.orderId = orderId;
        this.tradeSender = tradeSender;
        this.tradeReciver = tradeReciver;
        this.timestamp = timestamp;
        this.interOrigSys = interOrigSys;
        this.interI = interI;
        this.interVNum = interVNum;
        this.ver = ver;
        this.status = status;
        this.rejectCode = rejectCode;
        this.rejectReason = rejectReason;
        this.tradeOrigSys = tradeOrigSys;
    }
    public TraderDeal() {
        super();
    }

    @Override
    public String toString() {
        return "TraderDeal{" +
                "txnI=" + txnI +
                ", productId='" + productId + '\'' +
                ", volume=" + volume +
                ", price=" + price +
                ", notionalPrincipal=" + notionalPrincipal +
                ", orderId='" + orderId + '\'' +
                ", tradeSender=" + tradeSender +
                ", tradeReciver=" + tradeReciver +
                ", timestamp=" + timestamp +
                ", interOrigSys='" + interOrigSys + '\'' +
                ", interI=" + interI +
                ", interVNum=" + interVNum +
                ", ver=" + ver +
                ", status='" + status + '\'' +
                ", rejectCode=" + rejectCode +
                ", rejectReason='" + rejectReason + '\'' +
                ", tradeOrigSys='" + tradeOrigSys + '\'' +
                '}';
    }

    public void setStatus(StatusCode tpsProcessed) {
        this.status = tpsProcessed.toString();
    }
}
