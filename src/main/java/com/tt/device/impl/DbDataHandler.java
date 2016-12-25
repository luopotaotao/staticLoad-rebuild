package com.tt.device.impl;

import com.tt.device.DataHandler;
import com.tt.device.exception.DataHandleException;
import com.tt.device.model.SourceData;
import com.tt.model.InspectData;
import com.tt.service.BInspectServiceI;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created by tt on 2016/12/25.
 */
public class DbDataHandler implements DataHandler {
    private static final Logger logger = Logger.getLogger(DbDataHandler.class);
    @Autowired
    @Qualifier("bInspectServiceI")
    private BInspectServiceI bInspectService;
    @Override
    public String handle(SourceData data) {
        if(data!=null){
            if("END".equals(data.getEnd_status())){
                int count = bInspectService.updateStatus(data.getPrg(), data.getStzh(), data.getDevnb());
                if(count>0){
                    return "OK";
                }else{
                    logger.error(String.format("标记完成失败!工程:%s,桩号:%s,设备号:%s", data.getPrg(), data.getStzh(), data.getDevnb()));
                    throw new DataHandleException("更新数据库失败,请联系管理员处理!");
                }
            }else{
                Integer id = bInspectService.add(data.toInspectData());
                if (id != null && id > 0) {
                    return "OK";
                } else {
                    logger.error(String.format("保存失败:工程:%s,桩号:%s,设备号:%s", data.getPrg(), data.getStzh(), data.getDevnb()));
                    throw new DataHandleException("保存失败,请联系管理员!");
                }
            }
        }
        throw new DataHandleException("无数据");
    }
}
