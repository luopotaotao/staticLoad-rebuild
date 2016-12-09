package com.tt.dao;

import com.tt.model.User;

import java.util.List;

/**
 * Created by taotao on 2016/9/23.
 */
public interface UserDaoI extends BaseDaoI<User> {
    List<User> list(String name, Integer page, Integer pageSize);
}
