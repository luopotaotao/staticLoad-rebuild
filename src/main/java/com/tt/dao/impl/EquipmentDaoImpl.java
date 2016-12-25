package com.tt.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.tt.dao.EquipmentDaoI;
import com.tt.dao.impl.BaseDaoImpl;
import com.tt.model.Equipment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by taotao on 2016/9/23.
 */
@Repository("equipmentDao")
public class EquipmentDaoImpl extends BaseDaoImpl<Equipment> implements EquipmentDaoI {
    @Override
    public List<Equipment> find( Map<String,Object> params) {
        String name = (String) params.get("name");
        if(name!=null&&!name.trim().isEmpty()){
//            params.put("name","%"+name+"%");
            params.remove("name");
            params.put("code","%"+name+"%");
        }
        Criteria criteria = getCriteria(params).addOrder(Order.asc("id"));
        return criteria.list();
    }

    @Override
    public Equipment loadByCode(String code) {
        return (Equipment) getCriteria().add(Restrictions.eq("code",code)).uniqueResult();
    }

    @Override
    public Equipment loadByCodeIgnoreDept(String code) {
        List<Equipment> list = null;
        Map<String,Object> params = new HashMap<>();
        params.put("code",code);
        list = find("from Equipment e where e.code=:code",params);
        return list!=null&&list.size()>0?list.get(0):null;
    }
}
