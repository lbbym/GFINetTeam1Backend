package com.citi_team_one.tps.model;

public enum StatusCode {
    REQUESTED(1), PENDING(2),
    TPS_PROCESSED(3), PRICE_UNMATCHED(3),
    ACCEPTED(4), REJECTED(4), HELD(4);

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
