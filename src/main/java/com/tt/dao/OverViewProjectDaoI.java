package com.tt.dao;

import com.tt.model.OverViewProject;

import java.util.List;

/**
 * Created by tt on 2016/9/29.
 */
public interface OverViewProjectDaoI extends BaseDaoI<OverViewProject>{
    List<OverViewProject> list();
}
