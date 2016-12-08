package com.tt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tt.dao.InspectMethodDaoI;
import com.tt.model.InspectMethod;
import com.tt.service.InspectMethodServiceI;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by taotao on 2016/9/23.
 */
@Service("inspectMethodService")
@Transactional
public class InspectMethodServiceImpl implements InspectMethodServiceI {
    @Autowired
    private InspectMethodDaoI inspectMethodDao;

    @Override
    public InspectMethod get(Serializable id) {
        return inspectMethodDao.getById(id);
    }

    @Override
    public List<InspectMethod> list(Map<String,Object> params,Integer page,Integer pageSize) {
        Integer inspect_item_id = (Integer) params.get("inspect_item_id");
        String name = (String) params.get("name");
        List<InspectMethod> ret = inspectMethodDao.list(inspect_item_id,name);
        return ret;
    }

    @Override
    public InspectMethod add(InspectMethod inspectMethod) {
 //       inspectMethod.setDept_id(dept_id);
        inspectMethodDao.save(inspectMethod);
        return inspectMethod;
    }

    @Override
    public int del(List<Integer> ids) {
        if(ids==null||ids.size()<1){
            return 0;
        }
        Map<String, Object> params = new HashMap<>();

        params.put("ids", ids);
        return inspectMethodDao.executeHql("delete from InspectMethod where id in (:ids)", params);
    }

    @Override
    public InspectMethod update(InspectMethod inspectMethod) {
 //       inspectMethod.setDept_id(dept_id);
        inspectMethodDao.update(inspectMethod);
        return inspectMethod;
    }
}
