package com.tt.service.impl;

import com.tt.ext.security.MyUserDetails;
import com.tt.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.tt.dao.AreaObjDaoI;
import com.tt.dao.CompanyDaoI;
import com.tt.dao.ProjectDaoI;
import com.tt.model.AreaObj;
import com.tt.model.Company;
import com.tt.model.Project;
import com.tt.service.ProjectServiceI;
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
@Service("projectService")
@Transactional
public class ProjectServiceImpl implements ProjectServiceI {
    @Autowired
    private ProjectDaoI projectDao;

    @Override
    public Project get(Serializable id) {
        return projectDao.getById(id);
    }


    @Override
    public List<Project> list(Map<String, Object> params, Integer page, Integer pageSize) {

        Integer area_id = (Integer) params.get("area_id");
        String name = (String) params.get("name");
        StringBuilder hql = new StringBuilder("from Project WHERE dept_id=:dept_id");
        if (area_id != null && area_id != 0) {
            hql.append(" AND (province_id=:area_id or city_id in (select id from Area where pid=:area_id))");
        }
        if (name != null) {
            params.put("name", "%" + name + "%");
            hql.append(" AND (name like :name or code like :name) ");
        }

        List<Project> ret = projectDao.find(hql.toString(), params, page, pageSize);

        return ret;
    }

    @Override
    public List<Project> list(Map<String, Object> params) {
        List<Project> list;

        StringBuilder hql = new StringBuilder("select distinct p from Project p left join fetch p.children s left join fetch s.children plan WHERE p.dept_id=:dept_id");
        if (SessionUtil.hasRole("ROLE_CUSTOM")) {
            params = new HashMap<>();
            params.put("userId", SessionUtil.getUser().getId());
            hql.append(" and (plan.inspector.id=:userId or plan.majorInspector.id=:userId or plan.assistantInspector.id=:userId)");
            list = projectDao.find(hql.toString(), params);
        } else {
            list = projectDao.find(hql.toString());
        }

        if (list != null && !list.isEmpty()) {
            List<Integer> ids = list.stream().map(p -> p.getId()).collect(Collectors.toList());
            Map<Integer, Integer> statusMap = listStatus(ids);
            list.stream().forEach(item -> item.setStatus(statusMap.get(item.getId())));
        }

        return list;
    }

    private Map<Integer, Integer> listStatus(List<Integer> ids) {
        Map<Integer, Integer> ret = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        params.put("ids", ids);
        List<Object[]> list = projectDao.findBySql("SELECT p_id id,COUNT(*) started FROM \n" +
                "(SELECT id,inspect_scheme_id FROM b_inspect_plan WHERE inspect_scheme_id in(SELECT id FROM b_inspect_scheme WHERE inspect_project_id in(:ids))) p\n" +
                "LEFT JOIN(SELECT id,inspect_project_id p_id FROM b_inspect_scheme WHERE inspect_project_id in(:ids)) s ON p.inspect_scheme_id=s.id\n" +
                " GROUP BY p_id;", params);
        System.out.println(list);
        list.stream().forEach(row -> {
            ret.put((Integer) row[0], ((BigInteger) row[1]).intValue());
        });
        return ret;
    }

    //TODO delete?
    @Override
    public long count(Map<String, Object> params) {
        return 0;
    }

    @Override
    public Project add(Project project) {
        projectDao.save(project);
        return project;
    }

    @Override
    public Project update(Project project) {
        projectDao.update(project);
        return project;
    }

    @Override
    public int del(List<Integer> list) {
        Map<String, Object> params = new LinkedHashMap<>();

        params.put("ids", list);
        return projectDao.executeHql("delete from Project where id in (:ids)", params);
    }


    @Override
    public List<String> listStzh(String prg) {
        return projectDao.queryStzhByProjectCode(prg);
    }
}
