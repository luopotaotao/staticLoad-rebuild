package com.tt.dao;

import com.tt.dao.BaseDaoI;
import com.tt.model.InspectData;

import java.util.List;

/**
 * Created by taotao on 2016/9/23.
 */
public interface InspectDataDaoI extends BaseDaoI<InspectData> {
    InspectData loadLatestData(String PRG, String STZH);

    List<Object[]> getMaxLoadByPlanIds(List<Integer> ids);
    List<Object[]> getMaxOffsetByPlanIds(List<Integer> ids);
}
