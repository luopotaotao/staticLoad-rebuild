package com.tt.device.impl;

import com.alibaba.fastjson.JSON;
import com.tt.device.DataParser;
import com.tt.device.exception.DataFormatException;
import com.tt.device.model.SourceData;

/**
 * Created by tt on 2016/12/25.
 */
public class JsonDataParser implements DataParser {
    @Override
    public SourceData parseStr(String str) throws DataFormatException {
        SourceData ret = null;
        try{
            ret = JSON.parseObject(str, SourceData.class);
        }catch (Exception e){
            throw new DataFormatException("解析数据失败,请检查数据格式是否符合要求!");
        }
        return ret;
    }
}
