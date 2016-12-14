package com.tt.dao.impl;

import com.tt.dao.InspectorDaoI;
import com.tt.model.Inspector;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by taotao on 2016/9/23.
 */
@Repository("inspectorDao")
public class InspectorDaoImpl extends BaseDaoImpl<Inspector> implements InspectorDaoI {

    @Override
    public List<Inspector> list(Map<String,Object> params, Integer page, Integer pageSize) {
        String name = (String) params.get("name");
        Criteria criteria = getCriteria(page,pageSize).addOrder(Order.asc("id"));

        if(name!=null&&!name.trim().isEmpty()){
            criteria.add(Restrictions.like("name","%"+name+"%"));
        }
        return criteria.list();
    }

}
