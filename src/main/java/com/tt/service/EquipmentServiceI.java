package com.tt.service;

import com.tt.model.Equipment;

/**
 * Created by tt on 2016/10/2.
 */
public interface EquipmentServiceI extends BaseService<Equipment>{
    Equipment loadEquipmentByCode(String code);
//    List<Equipment> list(Integer dept_id, String name);
//TODO 额外参数
}
