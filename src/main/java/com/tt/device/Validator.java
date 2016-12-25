package com.tt.device;

import com.tt.device.model.SourceData;
import com.tt.device.exception.DataException;

/**
 * Created by tt on 2016/12/25.
 */
public interface Validator {
    boolean validate(SourceData data) throws DataException;
}
