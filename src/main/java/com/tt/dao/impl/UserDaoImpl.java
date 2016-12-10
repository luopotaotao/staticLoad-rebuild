package com.tt.dao.impl;

import com.tt.dao.InspectorDaoI;
import com.tt.dao.UserDaoI;
import com.tt.ext.security.MyUserDetails;
import com.tt.model.Inspector;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by taotao on 2016/9/23.
 */
@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<MyUserDetails> implements UserDaoI {

    @Override
    public List<MyUserDetails> list(String name, Integer page, Integer pageSize) {
        Criteria criteria = getCriteria(page,pageSize).addOrder(Order.asc("id"));
        if(name!=null&&!name.trim().isEmpty()){
            criteria.add(Restrictions.like("name","%"+name+"%"));
        }
        return criteria.list();
    }
}
