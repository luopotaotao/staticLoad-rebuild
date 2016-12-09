package com.tt.dao;

import com.tt.ext.security.MyUserDetails;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tt on 2016/11/29.
 */
@Repository
@Qualifier("myUserDetailsService")
@Transactional
public class TestMyUserRepository implements UserDetailsService{
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
