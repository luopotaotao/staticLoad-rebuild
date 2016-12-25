package com.tt.device;

import com.tt.device.exception.DataFormatException;
import com.tt.device.model.SourceData;

/**
 * Created by tt on 2016/12/25.
 */
public interface DataParser {
    /**
     * 将原始数据解析为 JavaBean
     * @param str
     * @return
     */
    SourceData parseStr(String str) throws DataFormatException;
}
