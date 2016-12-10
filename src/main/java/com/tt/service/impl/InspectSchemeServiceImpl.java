package com.tt.service.impl;

import com.tt.dao.*;
import com.tt.model.InspectScheme;
import com.tt.service.InspectSchemeServiceI;
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
@Service("inspectSchemeService")
@Transactional
public class InspectSchemeServiceImpl implements InspectSchemeServiceI {
    @Autowired
    private InspectSchemeDaoI inspectSchemeDao;

    @Override
    public InspectScheme get(Serializable id) {
        return inspectSchemeDao.getById(id);
    }

    @Override
    public List<InspectScheme> list(Map<String, Object> params) {
        List<InspectScheme> ret = inspectSchemeDao.find(params);
        return ret;
    }

    @Override
    public long count(Map<String, Object> params) {
        String name = (String) params.get("name");
//        params.put("dept_id", dept_id);
        if (name != null) {
            params.put("name", "%" + name + "%");
        }
        long ret = inspectSchemeDao.count(params);
        return ret;
    }

    @Override
    public InspectScheme add(InspectScheme inspectScheme) {
        inspectSchemeDao.save(inspectScheme);
        return inspectScheme;
    }

    @Override
    public int del(List<Integer> ids) {
        if (ids == null || ids.size() < 1) {
            return 0;
        }
        Map<String, Object> params = new HashMap<>();
//        params.put("dept_id", dept_id);
        params.put("ids", ids);
        return inspectSchemeDao.executeHql("delete from InspectScheme where id in (:ids)", params);
    }

    @Override
    public InspectScheme update(InspectScheme inspectScheme) {
        inspectSchemeDao.update(inspectScheme);
        return inspectScheme;
    }

}
