package com.tt.device.impl;

import com.tt.device.ValidateRule;
import com.tt.device.Validator;
import com.tt.device.exception.DataFormatException;
import com.tt.device.model.SourceData;

/**
 * Created by tt on 2016/12/25.
 */
public class EquipValidator implements Validator {
    private ValidateRule rule;

    public boolean validate(SourceData data) throws DataFormatException {
        return rule.validate(data);
    }

    public ValidateRule getRule() {
        return rule;
    }

    public void setRule(ValidateRule rule) {
        this.rule = rule;
    }
}
