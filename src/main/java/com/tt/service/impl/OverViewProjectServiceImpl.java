package com.tt.service.impl;

import com.tt.dao.OverViewProjectDaoI;
import com.tt.dao.ProjectDaoI;
import com.tt.ext.security.MyUserDetails;
import com.tt.model.OverViewProject;
import com.tt.model.Project;
import com.tt.service.OverViewProjectServiceI;
import com.tt.service.ProjectServiceI;
import com.tt.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by tt on 2016/9/29.
 */
@Service("overViewProjectService")
@Transactional
public class OverViewProjectServiceImpl implements OverViewProjectServiceI {
    @Autowired
    private OverViewProjectDaoI overViewProjectDao;


    @Override
    public List<OverViewProject> list(Map<String,Object> params) {

        String name = (String) params.get("name");
        StringBuilder hql = new StringBuilder("from OverViewProject WHERE dept_id=:dept_id");

        if (name != null) {
            params.put("name", "%" + name + "%");
            hql.append(" AND (name like :name or code like :name) ");
        }

        List<OverViewProject> ret = overViewProjectDao.find(hql.toString(), params);

        return ret;
    }

    @Secured(value = "hasAnyRole('SUPER','ADMIN')")
    @Override
    public List<OverViewProject> listByAreaId(Integer area_id) {
        StringBuilder hql = new StringBuilder("from OverViewProject WHERE dept_id=:dept_id");
        Map<String, Object> params = new HashMap<>();

        if (area_id != null) {
            params.put("area_id", area_id);
            hql.append(" AND (province_id=:area_id or city_id=:area_id or province_id in (select a.id from Area a where a.parent.id=:area_id))");
        }
        List<OverViewProject> list = overViewProjectDao.find(hql.toString(),params);
        if(list!=null&&!list.isEmpty()){
            List<Integer> ids = list.stream().map(p->p.getId()).collect(Collectors.toList());
            Map<Integer,Integer> statusMap = listStatus(ids);
            list.stream().forEach(item->item.setStatus(statusMap.get(item.getId())));
        }

        return list;
    }

    @Secured(value = "hasRole('CUSTOM')")
    @Override
    public List<OverViewProject> list() {
        String hql = "from OverViewProject p WHERE p.dept_id=:dept_id and p.id in (select plan.project.id from InspectPlan plan where plan.id in (select up.planId from UserPlan up where up.userId=:userId))";

        Map<String, Object> params = new HashMap<>();
        MyUserDetails userDetails = SessionUtil.getUser();
        params.put("dept_id",userDetails.getDept().getId());
        params.put("userId",userDetails.getId());

        List<OverViewProject> list = overViewProjectDao.find(hql,params);
        return list;
    }

    private Map<Integer,Integer> listStatus(List<Integer> ids){
        Map<Integer,Integer> ret = new HashMap<>();
        Map<String,Object> params = new HashMap<>();
        params.put("ids",ids);
        List<Object[]> list = overViewProjectDao.findBySql("SELECT p_id id,COUNT(*) started FROM \n" +
                "(SELECT id,inspect_scheme_id FROM b_inspect_plan WHERE inspect_scheme_id in(SELECT id FROM b_inspect_scheme WHERE inspect_project_id in(:ids))) p\n" +
                "LEFT JOIN(SELECT id,inspect_project_id p_id FROM b_inspect_scheme WHERE inspect_project_id in(:ids)) s ON p.inspect_scheme_id=s.id\n" +
                " GROUP BY p_id;",params);
        list.stream().forEach(row->{ret.put((Integer) row[0],((BigInteger) row[1]).intValue());});
        return ret;
    }

}
