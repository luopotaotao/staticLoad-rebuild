package com.tt.service.impl;

import com.tt.dao.InspectPlanDaoI;
import com.tt.model.InspectPlan;
import com.tt.service.InspectPlanServiceI;
import com.tt.web.exception.ExistException;
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
@Service("inspectPlanService")
@Transactional
public class InspectPlanServiceImpl implements InspectPlanServiceI {
    @Autowired
    private InspectPlanDaoI inspectPlanDao;

    @Override
    public InspectPlan get(Serializable id) {
        return inspectPlanDao.getById(id);
    }

    @Override
    public List<InspectPlan> list(Map<String,Object> params) {
        List<InspectPlan> ret = inspectPlanDao.find(params);;
        return ret;
    }

    @Override
    public long count(Map<String,Object> params) {
        String name = (String) params.get("name");
        if (name != null) {
            params.put("name", "%" + name + "%");
        }
        long ret = inspectPlanDao.count(params);
        return ret;
    }

    @Override
    public InspectPlan add(InspectPlan inspectPlan) {
        //TODO 检测是否存在STZH
        boolean isExist = inspectPlanDao.isExistStzh(inspectPlan.getProject().getId(),inspectPlan.getStzh());
        if(isExist){
            throw new ExistException("该工程下已有桩号:"+inspectPlan.getStzh());
        }
        inspectPlanDao.save(inspectPlan);
        return inspectPlan;
    }

    @Override
    public int del(List<Integer> ids) {
        if(ids==null||ids.size()<1){
            return 0;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("ids", ids);

        return inspectPlanDao.executeHql("delete from InspectPlan where id in (:ids)", params);
    }

    @Override
    public InspectPlan update(InspectPlan inspectPlan) {
        inspectPlanDao.update(inspectPlan);
        return inspectPlan;
    }
}
