package com.tt.dao.impl;

import com.tt.dao.AuthorityDaoI;
import com.tt.ext.security.Authority;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Created by tt on 2016/12/11.
 */
@Repository("authorityDao")
public class AuthorityDaoImpl extends BaseDaoImpl<Authority> implements AuthorityDaoI {
    @Override
    public Authority findByName(String name) {
        return (Authority) getCriteria().add(Restrictions.eq("authority",name)).uniqueResult();
    }
}
