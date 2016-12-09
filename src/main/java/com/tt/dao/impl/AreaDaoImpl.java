package com.tt.dao.impl;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.tt.dao.AreaDaoI;
import com.tt.dao.impl.BaseDaoImpl;
import com.tt.model.Area;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tt on 2016/9/29.
 */
@Repository("areaDao")
public class AreaDaoImpl extends BaseDaoImpl<Area> implements AreaDaoI {

    @Override
    public Area load(Integer id) {
        return super.getById(id);
    }

    @Override
    public int del(Integer id) {
        Map<String,Object> params = new HashMap<>();
        params.put("id",id);

        return executeSql("delete from b_area where (id=:id or pid=:id)",params);
    }

    @Override
    public Area getRoot() {
        return (Area) getCriteria().add(Restrictions.eq("level",(byte)0)).uniqueResult();
    }
}
