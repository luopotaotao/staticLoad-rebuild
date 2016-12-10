package com.tt.dao;

import com.tt.model.Inspector;

import java.util.List;

/**
 * Created by taotao on 2016/9/23.
 */
public interface InspectorDaoI extends BaseDaoI<Inspector> {
    List<Inspector> list(String name, Integer page, Integer pageSize);

}
