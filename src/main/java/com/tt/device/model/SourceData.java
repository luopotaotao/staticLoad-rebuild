package com.tt.device.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.tt.model.InspectData;
import org.springframework.beans.BeanUtils;

/**
 * Created by tt on 2016/11/25.
 */
public class SourceData extends InspectData {
    @JSONField(name = "STATUS")
    private String end_status;

    public String getEnd_status() {
        return end_status;
    }

    public void setEnd_status(String end_status) {
        this.end_status = end_status;
    }

    public InspectData toInspectData(){
        InspectData inspectData = new InspectData();
        BeanUtils.copyProperties(this,inspectData);
        return inspectData;
    }
}
