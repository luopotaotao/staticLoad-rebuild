package com.tt.service.impl;

import com.tt.dao.UserDaoI;
import com.tt.model.User;
import com.tt.service.DeptServiceI;
import com.tt.service.UserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by taotao on 2016/9/23.
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserServiceI {
    @Autowired
    private UserDaoI userDao;

    public User get(Integer id) {
        return userDao.getById(id);
    }

    @Override
    public User get(Serializable id) {
        return null;
    }

    @Override
    public List<User> list(Map<String,Object> params,Integer page,Integer pageSize) {
        String name = (String) params.get("name");
        Integer current_role = (Integer) params.get("role");
        List<User> ret = userDao.list( name, null,null);
        return ret;
    }

    @Override
    public User add(User user) {
        userDao.save(user);
        return user;
    }

    @Override
    public int del(List<Integer> ids) {
        if (ids == null || ids.size() < 1) {
            return 0;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("ids", ids);
        //TODO 校验是否有权限
        return userDao.executeHql("delete from User where id in (:ids)", params);
    }

    @Override
    public boolean isExist(String name) {
        Map<String,Object> params = new HashMap<>();
        params.put("name",name);
        return userDao.count("select count(*) from User as u where u.username=:name",params)>0;
    }

    @Override
    public User update(User user) {
        User oldUser = this.get(user.getId());
//        Dept dept = deptService.get(dept_id);
//        user.setDept(dept);
//        oldUser.setEmail(user.getEmail());
//        oldUser.setRole(user.getRole());
        userDao.update(oldUser);
        return user;
    }
}
