package com.tt.dao;

import com.tt.ext.security.Authority;

/**
 * Created by tt on 2016/12/11.
 */
public interface AuthorityDaoI extends BaseDaoI<Authority> {
    Authority findByName(String name);
}
