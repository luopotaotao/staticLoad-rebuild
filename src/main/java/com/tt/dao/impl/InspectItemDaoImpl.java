package com.tt.dao.impl;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;
import com.tt.dao.InspectItemDaoI;
import com.tt.dao.impl.BaseDaoImpl;
import com.tt.model.InspectItem;

import java.util.List;

/**
 * Created by taotao on 2016/9/23.
 */
@Repository("inspectItemDao")
public class InspectItemDaoImpl extends BaseDaoImpl<InspectItem> implements InspectItemDaoI {
    @Override
    public List<InspectItem> list(String name, Integer page, Integer pageSize) {
        Criteria c = super.getCriteria(page,pageSize);
        if(!isEmpty(name)){
            c.add(like("name",name));
        }
        return c.list();
    }

}
