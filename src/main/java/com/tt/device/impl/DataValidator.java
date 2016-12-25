package com.tt.device.impl;

import com.tt.device.ValidateRule;
import com.tt.device.Validator;
import com.tt.device.exception.DataFormatException;
import com.tt.device.model.SourceData;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by tt on 2016/12/25.
 */
public class DataValidator implements Validator{
    private List<ValidateRule> rules;

    public List<ValidateRule> getRules() {
        return rules;
    }

    public void setRules(List<ValidateRule> rules) {
        this.rules = rules;
    }

    public boolean validate(SourceData data) throws DataFormatException {
        rules.stream().forEach(rule -> rule.validate(data));
        return true;
    }
}
