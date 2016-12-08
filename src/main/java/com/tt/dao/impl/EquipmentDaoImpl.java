package com.tt.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.tt.dao.EquipmentDaoI;
import com.tt.dao.impl.BaseDaoImpl;
import com.tt.model.Equipment;

import java.util.List;

/**
 * Created by taotao on 2016/9/23.
 */
@Repository("equipmentDao")
public class EquipmentDaoImpl extends BaseDaoImpl<Equipment> implements EquipmentDaoI {
    @Override
    public List<Equipment> list( String name) {
        Criteria criteria = getCriteria().addOrder(Order.asc("id"));
        if(name!=null&&!name.trim().isEmpty()){
            criteria.add(like("name",name));
        }
//        if(page!=null&&pageSize!=null){
//            criteria.setFirstResult((page-1)*pageSize);
//            criteria.setMaxResults(pageSize);
//        }
        return criteria.list();
    }

    @Override
    public Equipment loadByCode(String code) {
        return (Equipment) getCriteria().add(Restrictions.eq("code",code)).uniqueResult();
    }
}
