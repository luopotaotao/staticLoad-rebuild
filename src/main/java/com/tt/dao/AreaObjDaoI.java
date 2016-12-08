package com.tt.dao;

import com.tt.dao.BaseDaoI;
import com.tt.model.AreaObj;

/**
 * Created by taotao on 2016/9/23.
 */
public interface AreaObjDaoI extends BaseDaoI<AreaObj> {
    AreaObj get(Integer id);
}
