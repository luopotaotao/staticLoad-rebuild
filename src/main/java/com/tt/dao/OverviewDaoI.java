package com.tt.dao;

import com.tt.dao.BaseDaoI;
import com.tt.model.Overview;

import java.util.List;

/**
 * Created by tt on 2016/10/1.
 */
public interface OverviewDaoI extends BaseDaoI<Overview> {
    List<Overview> queryOverview(Integer area_id);
    List<Overview> queryAll();
}
