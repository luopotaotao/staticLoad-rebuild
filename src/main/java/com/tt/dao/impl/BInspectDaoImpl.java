package com.tt.dao.impl;


import org.springframework.stereotype.Repository;
import com.tt.dao.BInspectDaoI;
import com.tt.dao.impl.BaseDaoImpl;
import com.tt.model.InspectData;

import java.util.HashMap;
import java.util.Map;


@Repository
public class BInspectDaoImpl extends BaseDaoImpl<InspectData> implements BInspectDaoI
{
    @Override
    public int addIns(InspectData ins)
    {
        this.save(ins);
        return ins.getId();
    }
    @Override
    public int updateStatus(String PRG, String STZH, String DevNB)
    {
        String hql = "update InspectData d set d.status=2 where d.devnb=:DevNB and d.prg=:PRG and d.stzh = :STZH";
//        String sql = "UPDATE `b_inspect_data` t SET t.`status`='2' WHERE t.`DevNB`='"+DevNB+"' AND t.`PRG`='"+PRG+"' AND t.`STZH`='"+STZH+"'";
//        this.executeSql(sql);
        Map<String,Object> params = new HashMap<>();
        params.put("PRG",PRG);
        params.put("STZH",STZH);
        params.put("DevNB",DevNB);
        return this.executeHql(hql,params);
    }
}
