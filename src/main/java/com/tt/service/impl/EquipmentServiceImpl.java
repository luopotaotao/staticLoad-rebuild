package com.tt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tt.dao.EquipmentDaoI;
import com.tt.model.Equipment;
import com.tt.service.EquipmentServiceI;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by taotao on 2016/9/23.
 */
@Service("equipmentService")
@Transactional
public class EquipmentServiceImpl implements EquipmentServiceI {
    @Autowired
    private EquipmentDaoI equipmentDao;

    @Override
    public Equipment get(Serializable id) {
        return equipmentDao.getById(id);
    }


    @Override
    public List<Equipment> list(Map<String, Object> params,Integer page,Integer pageSize) {
        String name = (String) params.get("name");
        List<Equipment> ret = equipmentDao.list( name);
        return ret;
    }

    @Override
    public Equipment add(Equipment equipment) {
        equipmentDao.save(equipment);
        return equipment;
    }

    @Override
    public int del(List<Integer> ids) {
        if (ids == null || ids.size() < 1) {
            return 0;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("ids", ids);

        return equipmentDao.executeHql("delete from Equipment where id in (:ids)", params);
    }

    @Override
    public Equipment update(Equipment equipment) {
        equipmentDao.update(equipment);
        return equipment;
    }

    @Override
    public Equipment loadEquipmentByCode(String code) {
        return equipmentDao.loadByCode(code);
    }
}
