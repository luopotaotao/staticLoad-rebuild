package com.tt.device.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by tt on 2016/11/25.
 */
public class EndData {
    @JSONField(name = "PRG")
    private String PRG;
    @JSONField(name = "STZH")
    private String STZH;
    @JSONField(name = "DevNB")
    private String DevNB;

    public EndData() {
    }

    public EndData(String PRG, String STZH, String devNB) {
        this.PRG = PRG;
        this.STZH = STZH;
        DevNB = devNB;
    }

    public String getPRG() {
        return PRG;
    }

    public void setPRG(String PRG) {
        this.PRG = PRG;
    }

    public String getSTZH() {
        return STZH;
    }

    public void setSTZH(String STZH) {
        this.STZH = STZH;
    }

    public String getDevNB() {
        return DevNB;
    }

    public void setDevNB(String devNB) {
        DevNB = devNB;
    }
}
