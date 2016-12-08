package com.tt.dao;

import com.tt.model.TestUser;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by tt on 2016/11/29.
 */
@Repository
public class TestUserRepository {
    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public TestUser findOne(long id) {
        return (TestUser) getSession().createCriteria(TestUser.class).add(Restrictions.eq("id", id)).uniqueResult();
    }

    public Long save(TestUser user) {
        getSession().save(user);
        return user.getId();
    }

    public TestUser loadLatestUser(String username) {
//        User user = (User) getSession().createCriteria(User.class).add(Restrictions.eq("username",username)).uniqueResult();
        TestUser user = (TestUser) getSession()
                .createQuery("from User u join fetch u.books b join fetch b.publishers p " +
                        "where u.username=:username and b.id=1  and p.id=1 and u.id in (select bb.id from Book bb)")
                .setString("username", username).uniqueResult();
        return user;//(User)getSession().createCriteria(User.class).add(Restrictions.eq("id", Property.forName("id").max())).uniqueResult();
    }
}
