package com.tt.dao;

import com.tt.dao.BaseDaoI;
import com.tt.model.InspectMethod;

import java.util.List;

/**
 * Created by taotao on 2016/9/23.
 */
public interface InspectMethodDaoI extends BaseDaoI<InspectMethod> {
    List<InspectMethod> list(Integer inspect_item_id, String name);
    List<InspectMethod> findByIds(List<Integer> ids);

}
