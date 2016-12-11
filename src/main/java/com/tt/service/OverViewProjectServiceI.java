package com.tt.service;

import com.tt.model.OverViewProject;

import java.util.List;

/**
 * Created by tt on 2016/9/29.
 */
public interface OverViewProjectServiceI extends BaseService<OverViewProject>{
    List<OverViewProject> listByAreaId(Integer area_id);
    List<OverViewProject> list();
}
