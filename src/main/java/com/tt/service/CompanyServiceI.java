package com.tt.service;

import com.tt.model.Company;

import java.util.List;

/**
 * Created by taotao on 2016/9/23.
 */
public interface CompanyServiceI extends BaseService<Company> {
//    List<Company> list(Byte typ,String name, int page, int PageSize);
//    long count(Byte typ,String name);
    //TODO 额外参数
    int del(List<Integer> ids);
}
