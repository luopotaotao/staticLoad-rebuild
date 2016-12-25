package com.tt.device.impl;

import com.tt.device.model.SourceData;

/**
 * Created by tt on 2016/12/25.
 */
public class PrgNeededRule extends FieldNeededRule {

    public PrgNeededRule() {
        super("工程编号", "PRG");
    }
    @Override
    String getVal(SourceData item) {
        return item.getPrg();
    }
}
