package com.tt.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tt.dao.AreaDaoI;
import com.tt.model.Area;
import com.tt.service.AreaServiceI;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tt on 2016/9/29.
 */
@Service("areaService")
@Transactional
public class AreaServiceImpl implements AreaServiceI {
    @Autowired
    private AreaDaoI areaDao;

    @Override
    public Area getRoot() {
        return areaDao.getRoot();
    }

    @Override
    public Area get(Serializable id) {
        Area ret = areaDao.getById(id);
        return ret;
    }

    @Override
    public Area add(Area area) {
        Area parent = areaDao.get(Area.class,area.getParent().getId());
        area.setLevel((byte) (parent.getLevel()+1));
        area.setParent(parent);
        areaDao.save(area);
        return area;
    }

    @Override
    public Area update(Area area) {
        areaDao.update(area);
        return area;
    }

    @Override
    public int del(Integer id) {
        return areaDao.del(id);
    }
    @Override
    public int del(List<Integer> ids) {
        if(ids==null||ids.size()<1){
            return 0;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ids", ids);

        return areaDao.executeHql("delete from Area where id in (:ids)", params);
    }

    /**
     *
     * @param pid pid为0时,查找level为0,也就是全国下面的省份
     * @return
     */
    @Override
    public List<Map<String, Object>> queryAreaByPid(Integer pid) {
        String sql = null;
        Map<String,Object> params = new HashMap<>();
        if(pid==0){
            params.put("level",(byte)1);
            sql = "select new map(a.id as id,a.text as text) from Area a where level=:level and a.dept_id=:dept_id";
        }else{
            params.put("pid",pid);
            sql = "select new map(a.id as id,a.text as text) from Area a where pid=:pid and a.dept_id=:dept_id";
        }


        return areaDao.findList(sql,params);
    }

    @Override
    public List<Area> list(Map<String, Object> params, Integer page, Integer pageSize) {
        return null;
    }

    @Override
    public long count(Map<String, Object> params) {
        return 0;
    }
}
