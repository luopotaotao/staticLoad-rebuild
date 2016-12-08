package com.tt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tt.dao.*;
import com.tt.model.File;
import com.tt.model.InspectScheme;
import com.tt.service.InspectSchemeServiceI;
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
    @Autowired
    private ProjectDaoI projectDao;
    @Autowired
    private InspectItemDaoI inspectItemDao;
    @Autowired
    private DeptDaoI deptDao;

    @Autowired
    private FileDaoI fileDao;

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
        resetScheme(inspectScheme);
//        inspectScheme.setDept(deptDao.getById(dept_id));
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
        resetScheme(inspectScheme);
        inspectSchemeDao.update(inspectScheme);
        return inspectScheme;
    }

    private void resetScheme(InspectScheme inspectScheme) {
        inspectScheme.setProject(projectDao.getById(inspectScheme.getProject().getId()));
        inspectScheme.setInspectItem(inspectItemDao.getById(inspectScheme.getInspectItem().getId()));

        File approval_file = inspectScheme.getApproval_file();
        if (approval_file != null && approval_file.getUuid() != null) {
            inspectScheme.setApproval_file(fileDao.getById(approval_file.getUuid()));
        } else {
            inspectScheme.setApproval_file(null);
        }
        File inspect_file = inspectScheme.getInspect_file();
        if (inspect_file != null && inspect_file.getUuid() != null) {
            inspectScheme.setInspect_file(fileDao.getById(inspect_file.getUuid()));
        } else {
            inspectScheme.setInspect_file(null);
        }
    }
}
