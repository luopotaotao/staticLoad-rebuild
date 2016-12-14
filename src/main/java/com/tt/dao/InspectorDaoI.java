package com.tt.dao;

import com.tt.model.Inspector;

import java.util.List;
import java.util.Map;

/**
 * Created by taotao on 2016/9/23.
 */
public interface InspectorDaoI extends BaseDaoI<Inspector> {
    List<Inspector> list(Map<String,Object> params, Integer page, Integer pageSize);

}
