package com.tt.dao;

import com.tt.dao.BaseDaoI;
import com.tt.model.Equipment;

import java.util.List;

/**
 * Created by taotao on 2016/9/23.
 */
public interface EquipmentDaoI extends BaseDaoI<Equipment> {
    List<Equipment> list(String name);
    Equipment loadByCode(String coded);
}
