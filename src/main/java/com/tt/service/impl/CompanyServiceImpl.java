package com.tt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tt.dao.CompanyDaoI;
import com.tt.model.Company;
import com.tt.service.CompanyServiceI;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by taotao on 2016/9/23.
 */
@Service("companyService")
@Transactional
public class CompanyServiceImpl implements CompanyServiceI {
    @Autowired
    private CompanyDaoI companyDao;

    @Override
    public Company get(Serializable id) {
        return companyDao.getById(id);
    }

    @Override
    public List<Company> list(Map<String,Object> params, Integer page, Integer PageSize) {

        Byte typ = (Byte) params.get("typ");
        String name = (String) params.get("name");
        StringBuilder hql = new StringBuilder("from Company WHERE dept_id=:dept_id");
        if(typ!=null&&typ!=0){
            params.put("typ",typ);
            hql.append(" AND typ=:typ ");
        }
        if(name!=null){
            params.put("name","%"+name+"%");
            hql.append(" AND name like :name ");
        }
        List<Company> ret = companyDao.find(hql.toString(), params, page, PageSize);
        return ret;
    }

    @Override
    public long count(Map<String,Object> params) {
        StringBuilder hql = new StringBuilder("select count(*) from Company WHERE dept_id=:dept_id");
        Byte typ = (Byte) params.get("typ");
        String name = (String) params.get("name");

        if(typ!=null&&typ!=0){
            params.put("typ",typ);
            hql.append(" AND typ=:typ ");
        }
        if(name!=null){
            params.put("name","%"+name+"%");
            hql.append(" AND name like :name ");
        }
        long ret = companyDao.count(hql.toString(), params);
        return ret;
    }

    @Override
    public Company add(Company company) {
   //     company.setDept_id(dept_id);
        companyDao.save(company);
        return company;
    }

    @Override
    public int del(List<Integer> ids) {
        if(ids==null||ids.size()<1){
            return 0;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ids", ids);

        return companyDao.executeHql("delete from Company where id in (:ids)", params);
    }

    @Override
    public Company update(Company company) {
 //       company.setDept_id(dept_id);
        companyDao.update(company);
        return company;
    }
}
