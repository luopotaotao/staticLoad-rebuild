package com.tt.dao.impl;

import org.springframework.stereotype.Repository;
import com.tt.dao.AreaObjDaoI;
import com.tt.dao.impl.BaseDaoImpl;
import com.tt.model.AreaObj;

/**
 * Created by taotao on 2016/9/23.
 */
@Repository("areaObjDao")
public class AreaObjDaoImpl extends BaseDaoImpl<AreaObj> implements AreaObjDaoI {
    @Override
    public AreaObj get(Integer id) {
        return super.getById(id);
    }
}
