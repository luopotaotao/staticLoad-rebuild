package com.tt.device.impl;

import com.tt.device.model.SourceData;

/**
 * Created by tt on 2016/12/25.
 */
public class StzhNeededRule extends FieldNeededRule {

    public StzhNeededRule() {
        super("桩号", "STZH");
    }
    @Override
    String getVal(SourceData item) {
        return item.getStzh();
    }
}
