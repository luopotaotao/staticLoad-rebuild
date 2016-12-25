package com.tt.device.impl;

import com.tt.device.ValidateRule;
import com.tt.device.exception.DataFormatException;
import com.tt.device.exception.EquipException;
import com.tt.device.model.SourceData;
import com.tt.model.Equipment;
import com.tt.service.EquipmentServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Date;

/**
 * Created by tt on 2016/12/25.
 */
public class EquipAvailRule implements ValidateRule {
    @Autowired
    @Qualifier("equipmentService")
    private EquipmentServiceI equipmentService;
    @Override
    public boolean validate(SourceData data) throws DataFormatException {
        Equipment equipment = equipmentService.loadByCodeIgnoreDept(data.getDevnb());
        if(equipment!=null){
            if(equipment.getExpiredDate().before(new Date())){
                throw new EquipException("DevNB expired");
            }
        }else{
            throw new EquipException("no DevNB registered");
        }
        return true;
    }
}
