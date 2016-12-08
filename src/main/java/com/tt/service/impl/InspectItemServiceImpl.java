package com.tt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tt.dao.InspectItemDaoI;
import com.tt.model.InspectItem;
import com.tt.service.InspectItemServiceI;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by taotao on 2016/9/23.
 */
@Service("inspectItemService")
@Transactional
public class InspectItemServiceImpl implements InspectItemServiceI {
    @Autowired
    private InspectItemDaoI inspectItemDao;

    @Override
    public InspectItem get(Serializable id) {
        return inspectItemDao.getById(id);
    }

    @Override
    public List<InspectItem> list(Map<String,Object> params, Integer page, Integer PageSize) {
        String name = (String) params.get("name");
        List<InspectItem> ret = inspectItemDao.list(name, page, PageSize);
        return ret;
    }
    @Override
    public long count(Map<String,Object> params) {
        StringBuilder hql = new StringBuilder("select count(*) from InspectItem WHERE dept_id=:dept_id");

        String name = (String) params.get("name");
        if(name!=null){
            params.put("name","%"+name+"%");
            hql.append(" AND name like :name ");
        }
        long ret = inspectItemDao.count(hql.toString(), params);
        return ret;
    }

    @Override
    public InspectItem add(InspectItem inspectItem) {
 //       inspectItem.setDept_id(dept_id);
        inspectItemDao.save(inspectItem);
        return inspectItem;
    }

    @Override
    public int del(List<Integer> ids) {
        if(ids==null||ids.size()<1){
            return 0;
        }
        Map<String, Object> params = new HashMap<>();

        params.put("ids", ids);
        return inspectItemDao.executeHql("delete from InspectItem where id in (:ids)", params);
    }

    @Override
    public InspectItem update(InspectItem inspectItem) {
 //       inspectItem.setDept_id(dept_id);
        inspectItemDao.update(inspectItem);
        return inspectItem;
    }
}
