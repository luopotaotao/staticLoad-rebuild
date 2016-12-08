package com.tt.dao;

import com.tt.dao.BaseDaoI;
import com.tt.model.InspectItem;

import java.util.List;

/**
 * Created by taotao on 2016/9/23.
 */
public interface InspectItemDaoI extends BaseDaoI<InspectItem> {
    List<InspectItem> list(String name, Integer page, Integer pageSize);
}
