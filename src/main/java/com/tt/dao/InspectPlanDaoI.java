package com.tt.dao;

import com.tt.dao.BaseDaoI;
import com.tt.model.InspectPlan;

import java.util.List;

/**
 * Created by taotao on 2016/9/23.
 */
public interface InspectPlanDaoI extends BaseDaoI<InspectPlan> {
    List<InspectPlan> list(String name, Integer page, Integer pageSize);
    InspectPlan getByPrgStzhDevNB(String prg, String stzh, String DevNB);
    boolean isExistStzh(Integer project_id, String stzh);

}
