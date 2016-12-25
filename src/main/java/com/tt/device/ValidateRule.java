package com.tt.device;

import com.tt.device.exception.DataFormatException;
import com.tt.device.model.SourceData;

import java.util.function.Predicate;

/**
 * Created by tt on 2016/12/25.
 */
public interface ValidateRule {
    boolean validate(SourceData data) throws DataFormatException;
}
