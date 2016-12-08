package com.tt.service;

import com.tt.model.Area;

import java.util.List;
import java.util.Map;

/**
 * Created by tt on 2016/9/29.
 */
public interface AreaServiceI extends BaseService<Area>{
    int del(Integer id);
    List<Map<String,Object>> queryAreaByPid(Integer pid);
}
