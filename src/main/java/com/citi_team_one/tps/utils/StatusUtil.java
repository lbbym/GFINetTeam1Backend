package com.citi_team_one.tps.utils;

import com.citi_team_one.tps.model.StatusCode;

public class StatusUtil {
    public static Integer stastr2int(String statusString) {
        return StatusCode.valueOf(statusString).getStatusVersionNum();
    }
}
