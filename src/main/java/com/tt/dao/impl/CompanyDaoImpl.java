package com.tt.dao.impl;

import org.springframework.stereotype.Repository;
import com.tt.dao.CompanyDaoI;
import com.tt.dao.impl.BaseDaoImpl;
import com.tt.model.Company;

/**
 * Created by taotao on 2016/9/23.
 */
@Repository("companyDao")
public class CompanyDaoImpl extends BaseDaoImpl<Company> implements CompanyDaoI {
    @Override
    public Company get(Integer id) {
        return super.getById(id);
    }
}
