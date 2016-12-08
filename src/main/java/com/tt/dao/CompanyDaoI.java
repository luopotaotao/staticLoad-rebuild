package com.tt.dao;

import com.tt.dao.BaseDaoI;
import com.tt.model.Company;

/**
 * Created by taotao on 2016/9/23.
 */
public interface CompanyDaoI extends BaseDaoI<Company> {
    Company get(Integer id);
}
