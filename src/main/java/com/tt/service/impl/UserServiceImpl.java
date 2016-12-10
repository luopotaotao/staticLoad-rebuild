package com.tt.service.impl;

import com.tt.dao.UserDaoI;
import com.tt.ext.security.MyUserDetails;
import com.tt.service.UserServiceI;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by taotao on 2016/9/23.
 */
@Service
@Qualifier("myUserDetailsService")
@Transactional
public class UserServiceImpl implements UserServiceI,UserDetailsService {
    @Autowired
    private UserDaoI userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public MyUserDetails get(Integer id) {
        return userDao.getById(id);
    }

    @Override
    public MyUserDetails get(Serializable id) {
        return null;
    }

    @Override
    public List<MyUserDetails> list(Map<String,Object> params, Integer page, Integer pageSize) {
        String name = (String) params.get("name");
        Integer current_role = (Integer) params.get("role");
        List<MyUserDetails> ret = userDao.list( name, null,null);
        return ret;
    }

    @Override
    public MyUserDetails add(MyUserDetails user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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
        return userDao.executeHql("delete from MyUserDetails where id in (:ids)", params);
    }

    @Override
    public boolean isExist(String name) {
        Map<String,Object> params = new HashMap<>();
        params.put("name",name);
        return userDao.count("select count(*) from MyUserDetails as u where u.username=:name",params)>0;
    }

    @Override
    public MyUserDetails update(MyUserDetails MyUserDetails) {
        MyUserDetails oldMyUserDetails = this.get(MyUserDetails.getId());
//        Dept dept = deptService.get(dept_id);
//        MyUserDetails.setDept(dept);
//        oldMyUserDetails.setEmail(MyUserDetails.getEmail());
//        oldMyUserDetails.setRole(MyUserDetails.getRole());
        userDao.update(oldMyUserDetails);
        return MyUserDetails;
    }
    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public MyUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        MyUserDetails userDetails = (MyUserDetails) getSession().createCriteria(MyUserDetails.class).add(Restrictions.eq("username", username)).uniqueResult();
        if(userDetails==null){
            throw new UsernameNotFoundException("系统中不存在该用户!");
        }
        return userDetails;
    }
}
