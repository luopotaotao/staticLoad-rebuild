package com.tt.dao.impl;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.tt.dao.InspectDataDaoI;
import com.tt.dao.impl.BaseDaoImpl;
import com.tt.model.InspectData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by taotao on 2016/9/23.
 */
@Repository("inspectDataDao")
public class InspectDataDaoImpl extends BaseDaoImpl<InspectData> implements InspectDataDaoI {
    @Override
    public InspectData loadLatestData(String PRG, String STZH) {
        InspectData ret = null;
        Integer id = getMaxId(PRG,STZH);
        if(id!=null){
            ret = (InspectData) getCriteria().add(Restrictions.eq("prg",PRG)).add(Restrictions.eq("stzh",STZH)).add(Restrictions.eq("id",id)).uniqueResult();
        }
        return ret;
    }
    private Integer getMaxId(String PRG, String STZH){
        Integer id = null;
        String sql = "SELECT id FROM b_inspect_data " +
                " WHERE PRG=:PRG " +
                " AND STZH=:STZH " +
                " AND dept_id=:dept_id " +
                " AND Time = (SELECT MAX(Time) FROM b_inspect_data WHERE PRG=:PRG AND STZH=:STZH AND dept_id=:dept_id ) limit 1";
        Map<String,Object> params = new HashMap<>();
        params.put("PRG",PRG);
        params.put("STZH",STZH);

        List list = findBySql(sql,params);
        if(list!=null&&list.size()>0){
            try {
                id  = (Integer)list.get(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return id;
    }
    @Override
    public List<Object[]> getMaxLoadByPlanIds(List<Integer> ids) {
        String sql = "select plan_id,max(avg_hzjc) from (select * from b_inspect_data where plan_id in(:ids)) a GROUP BY plan_id";
        Map<String,Object> params = new HashMap<>();
        params.put("ids",ids);
        return findBySql(sql,params);
    }

    @Override
    public List<Object[]> getMaxOffsetByPlanIds(List<Integer> ids) {
        String sql = "select plan_id,max(avg_wyjc) from (select * from b_inspect_data where plan_id in(:ids)) a GROUP BY plan_id";
        Map<String,Object> params = new HashMap<>();
        params.put("ids",ids);
        return findBySql(sql,params);
    }
}
