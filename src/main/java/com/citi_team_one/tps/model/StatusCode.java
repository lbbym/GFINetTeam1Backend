package com.citi_team_one.tps.model;

public enum StatusCode {
    REQUESTED(), PENDING(), TPS_PROCESSED(), ACCEPTED(), REJECTED(),
    PRICE_UNMATCHED(),
    HELD();

    private RejectCode rejectCode;

    private StatusCode() {
        rejectCode = null;
    }
}
