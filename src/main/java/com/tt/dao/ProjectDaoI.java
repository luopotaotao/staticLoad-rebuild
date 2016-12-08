package com.tt.dao;

import com.tt.dao.BaseDaoI;
import com.tt.model.Project;

import java.util.List;

/**
 * Created by tt on 2016/9/29.
 */
public interface ProjectDaoI extends BaseDaoI<Project>{
    List<Project> queryProjectsByAreaId(Integer area_id);
    List<String> queryStzhByProjectCode(String prg);
}
