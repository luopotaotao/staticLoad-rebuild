package com.tt.service;

import com.tt.model.Overview;

import java.util.List;

/**
 * Created by tt on 2016/10/1.
 */
public interface OverviewServiceI {
    List<Overview> queryOverviews(Integer area_id);
    List<Overview> queryAll();
}
