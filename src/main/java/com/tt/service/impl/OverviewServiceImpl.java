package com.tt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tt.dao.OverviewDaoI;
import com.tt.model.Overview;
import com.tt.service.OverviewServiceI;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tt on 2016/10/1.
 */
@Service
@Transactional
public class OverviewServiceImpl implements OverviewServiceI {
    @Autowired
    private OverviewDaoI overviewDao;
    @Override
    public List<Overview> queryOverviews(Integer area_id) {
        List<Overview> overviews = overviewDao.queryOverview(area_id);
        return overviews;
    }

    @Override
    public List<Overview> queryAll() {
        List<Overview> overviews = overviewDao.queryAll();
        return overviews;
    }
}
