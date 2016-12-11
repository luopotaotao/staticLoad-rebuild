package com.tt.dao.impl;

import com.tt.dao.BaseDaoI;
import com.tt.ext.security.MyUserDetails;
import com.tt.util.SessionUtil;
import org.springframework.stereotype.Repository;
import com.tt.dao.OverviewDaoI;
import com.tt.dao.impl.BaseDaoImpl;
import com.tt.model.Overview;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tt on 2016/10/1.
 */
@Repository("overViewDao")
public class OverviewDaoImpl extends BaseDaoImpl<Overview> implements OverviewDaoI {

    @Override
    public List<Overview> queryOverview(Integer area_id) {
        Map params = new HashMap();
        params.put("area_id",area_id);

        List<Overview> ret = find("from Overview where id=:area_id and dept_id=:dept_id",params);
        return ret;
    }

    @Override
    public List<Overview> queryAll() {
        Map params = new HashMap();
        List<Overview> ret = find("from Overview where level=0 and dept_id=:dept_id",params);
        return ret;
    }
}
