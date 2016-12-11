//package com.tt.service.impl;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import com.tt.dao.InspectProjectDaoI;
//import com.tt.model.InspectProject;
//import com.tt.service.InspectProjectServiceI;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.io.Serializable;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by taotao on 2016/9/23.
// */
//@Service("inspectProjectService")
//@Transactional
//public class InspectProjectServiceImpl implements InspectProjectServiceI {
//    @Autowired
//    private InspectProjectDaoI inspectProjectDao;
//
//    @Override
//    public InspectProject get(Serializable id) {
//        return inspectProjectDao.getById(id);
//    }
//
//    @Override
//    public List<InspectProject> list(Map<String,Object> params, Integer page, Integer PageSize) {
//        StringBuilder hql = new StringBuilder("from InspectProject WHERE");
//
//        String name = (String) params.get("name");
//        if(name!=null){
//            params.put("name","%"+name+"%");
//            hql.append(" AND name like :name ");
//        }
//
//        List<InspectProject> ret = inspectProjectDao.find(hql.toString(), params, page, PageSize);
//        return ret;
//    }
//
//    @Override
//    public long count( Map<String,Object> params) {
//        StringBuilder hql = new StringBuilder("select count(*) from InspectProject WHERE");
//
//        String name = (String) params.get("name");
//        if(name!=null){
//            params.put("name","%"+name+"%");
//            hql.append(" AND name like :name ");
//        }
//        long ret = inspectProjectDao.count(hql.toString(), params);
//        return ret;
//    }
//
//    @Override
//    public InspectProject add(InspectProject inspectProject) {
////        inspectProject.setDept_id(dept_id);
//        inspectProjectDao.save(inspectProject);
//        return inspectProject;
//    }
//
//    @Override
//    public int del(List<Integer> ids) {
//        if(ids==null||ids.size()<1){
//            return 0;
//        }
//        Map<String, Object> params = new HashMap<>();
//        params.put("ids", ids);
//
//        return inspectProjectDao.executeHql("delete from InspectProject where id in (:ids)", params);
//    }
//
//    @Override
//    public InspectProject update(InspectProject inspectProject) {
////        inspectProject.setDept_id(dept_id);
//        inspectProjectDao.update(inspectProject);
//        return inspectProject;
//    }
//}
