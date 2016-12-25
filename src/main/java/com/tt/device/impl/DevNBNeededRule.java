package com.tt.device.impl;

import com.tt.device.model.SourceData;

/**
 * Created by tt on 2016/12/25.
 */
public class DevNBNeededRule extends FieldNeededRule {

    public DevNBNeededRule() {
        super("设备编号", "DevNB");
    }
    @Override
    String getVal(SourceData item) {
        return item.getDevnb();
    }
}
