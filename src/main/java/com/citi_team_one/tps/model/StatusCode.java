package com.citi_team_one.tps.model;

public enum StatusCode {
    REQUESTED(0),
    PENDING(1), PRICE_UNMATCHED(1),
    TPS_PROCESSED(2), HELD(2),
    ACCEPTED(3), REJECTED(3);

    private Integer statusVersionNum;
    private RejectCode rejectCode;

    private StatusCode(Integer statusVersionNum) {
        rejectCode = null;
        this.statusVersionNum = statusVersionNum;
    }

    public Integer getStatusVersionNum() {
        return statusVersionNum;
    }
}
