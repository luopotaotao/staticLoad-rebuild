package com.tt.service.impl;

import com.tt.dao.InspectorDaoI;
import com.tt.model.Inspector;
import com.tt.service.InspectorServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by taotao on 2016/9/23.
 */
@Service("userService")
@Transactional
public class InspectorServiceImpl implements InspectorServiceI {
    @Autowired
    private InspectorDaoI inspectorDao;

    public Inspector get(Integer id) {
        return inspectorDao.getById(id);
    }

    @Override
    public Inspector get(Serializable id) {
        return null;
    }

    @Override
    public List<Inspector> list(Map<String,Object> params, Integer page, Integer pageSize) {
        List<Inspector> ret = inspectorDao.list( params, null,null);
        return ret;
    }

    @Override
    public Inspector add(Inspector inspector) {
        inspectorDao.save(inspector);
        return inspector;
    }

    @Override
    public int del(List<Integer> ids) {
        if (ids == null || ids.size() < 1) {
            return 0;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("ids", ids);
        //TODO 校验是否有权限
        return inspectorDao.executeHql("delete from Inspector where id in (:ids)", params);
    }

    @Override
    public Inspector update(Inspector inspector) {
        Inspector oldInspector = this.get(inspector.getId());
//        Dept dept = deptService.get(dept_id);
//        inspector.setDept(dept);
//        oldInspector.setEmail(inspector.getEmail());
//        oldInspector.setRole(inspector.getRole());
        inspectorDao.update(oldInspector);
        return inspector;
    }
}
