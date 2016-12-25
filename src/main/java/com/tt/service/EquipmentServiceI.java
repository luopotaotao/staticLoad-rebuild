package com.tt.service;

import com.tt.model.Equipment;

/**
 * Created by tt on 2016/10/2.
 */
public interface EquipmentServiceI extends BaseService<Equipment>{
    Equipment loadEquipmentByCode(String code);
    Equipment loadByCodeIgnoreDept(String code);
    boolean isExist(String code);
}
