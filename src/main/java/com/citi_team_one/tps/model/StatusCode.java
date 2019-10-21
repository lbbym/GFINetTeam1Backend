package com.citi_team_one.tps.model;

public enum StatusCode {
    REQUESTED(), PENDING(), TPS_PROCESSED(), ACCEPTED(), REJECTED();

    private RejectCode rejectCode;

    private StatusCode() {
        rejectCode = null;
    }
}
