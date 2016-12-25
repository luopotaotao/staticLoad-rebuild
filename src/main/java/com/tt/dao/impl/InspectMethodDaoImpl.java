package com.tt.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.tt.dao.InspectMethodDaoI;
import com.tt.dao.impl.BaseDaoImpl;
import com.tt.model.InspectMethod;

import java.util.List;

/**
 * Created by taotao on 2016/9/23.
 */
@Repository("inspectMethodDao")
public class InspectMethodDaoImpl extends BaseDaoImpl<InspectMethod> implements InspectMethodDaoI {
    @Override
    public List<InspectMethod> list(Integer inspect_item_id, String name) {
        Criteria c = getCriteria().add(Restrictions.eq("inspect_item_id",inspect_item_id));
        if(!isEmpty(name)){
            c.add(Restrictions.like("name","%"+name+"%"));
        }
        return c.list();
    }

    @Override
    public List<InspectMethod> findByIds(List<Integer> ids) {
        return getCriteria().add(Restrictions.in("id",ids)).list();
    }
}
