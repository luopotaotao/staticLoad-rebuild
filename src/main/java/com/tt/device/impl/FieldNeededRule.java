package com.tt.device.impl;

import com.tt.device.ValidateRule;
import com.tt.device.exception.DataFormatException;
import com.tt.device.model.SourceData;

/**
 * Created by tt on 2016/12/25.
 */
public abstract class FieldNeededRule implements ValidateRule {
    private final String template ="报文格式错误:需要有字段 %s:%s,且值不能为空";
    private String name;
    private String filed;
    @Override
    public boolean validate(SourceData item) throws DataFormatException {
        String val =getVal(item);
        if (val == null || val.trim().isEmpty()) {
            throw new DataFormatException(String.format(template, name, filed));
        }
        return true;
    }

    abstract String getVal(SourceData item);

    public FieldNeededRule(String name, String filed) {
        this.name = name;
        this.filed = filed;
    }
}
