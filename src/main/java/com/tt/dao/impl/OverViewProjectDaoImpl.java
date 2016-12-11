package com.tt.dao.impl;

import com.tt.dao.OverViewProjectDaoI;
import com.tt.dao.ProjectDaoI;
import com.tt.model.OverViewProject;
import com.tt.model.Project;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by tt on 2016/9/29.
 */
@Repository("overViewProjectDaoI")
public class OverViewProjectDaoImpl extends BaseDaoImpl<OverViewProject> implements OverViewProjectDaoI {
    @Override
    public List<OverViewProject> list() {
        return null;
    }
}
