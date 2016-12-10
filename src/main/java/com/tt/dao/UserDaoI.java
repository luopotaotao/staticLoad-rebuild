package com.tt.dao;

import com.tt.ext.security.MyUserDetails;
import com.tt.model.Inspector;

import java.util.List;

/**
 * Created by taotao on 2016/9/23.
 */
public interface UserDaoI extends BaseDaoI<MyUserDetails> {
    List<MyUserDetails> list(String name, Integer page, Integer pageSize);
}
