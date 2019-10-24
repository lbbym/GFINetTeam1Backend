package com.citi_team_one.tps.model;

public enum RejectCode {
    STALE_DATA(), INVALID_REQUEST();

    private String rejectReason;

    private RejectCode() {
        setRejectReason("NA");
    }

    private RejectCode(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }
}
