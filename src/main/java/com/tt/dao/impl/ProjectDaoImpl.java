package com.tt.dao.impl;

import org.springframework.stereotype.Repository;
import com.tt.dao.ProjectDaoI;
import com.tt.dao.impl.BaseDaoImpl;
import com.tt.model.Project;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by tt on 2016/9/29.
 */
@Repository("projectDaoI")
public class ProjectDaoImpl extends BaseDaoImpl<Project> implements ProjectDaoI {
    @Override
    public List<Project> queryProjectsByAreaId(Integer area_id) {
//        String sql = "select * from Project " +
//                "where " +
//                "province_id =:area_id " +
//                "or city_id in (select id form b_area where pid=:area_id)";
        String hql = "from Project";
        Map<String,Object> params = new HashMap<>();
        params.put("area_id",area_id);
//        List<Project> ret = this.findBySql(Project.class,sql,params);
        List<Project> ret = find(hql,params);
        return ret;
    }

    @Override
    public List<String> queryStzhByProjectCode(String prg) {
        List<String> ret = null;
        Map<String,Object> params = new HashMap<>();
        params.put("prg",prg);
        List<Object[]> rows = findBySql("select distinct stzh where prg=:prg order by stzh",params);
        if(rows!=null&&rows.size()>0){
            ret = new LinkedList<>();
            for (int i = 0; i < rows.size(); i++) {
                ret.add((String)rows.get(i)[0]);
            }
        }
        return ret;
    }
}
