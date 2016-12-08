package com.tt.dao;

import com.tt.dao.BaseDaoI;
import com.tt.model.Area;

/**
 * Created by tt on 2016/9/29.
 */
public interface AreaDaoI extends BaseDaoI<Area>{
    Area load(Integer id);
//
//    int add(Area area);
//
//    int update(Area area);
//
//
    int del(Integer id);
}
