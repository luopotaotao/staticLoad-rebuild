//package com.tt.service;
//
//import com.tt.model.TestUser;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcOperations;
//import org.springframework.stereotype.Service;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpSession;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
///**
// * Created by tt on 2016/11/21.
// */
//@Service("userService")
//public class TestUserServiceImpl implements TestUserService {
//    @Autowired
//    private JdbcOperations jdbcOperations;
//
//    @Override
//    public List<TestUser> list(Integer page, Integer rows) {
//        HttpSession session = ((ServletRequestAttributes) RequestContextHolder
//                .getRequestAttributes()).getRequest().getSession();
//        System.out.println(session==null);
//        List<TestUser> ret = IntStream.range((page-1)*rows,page*rows).mapToObj(i->new TestUser(new Long(i),"name"+i, (Math.random()>0.5? TestUser.GENDER.FEMALE: TestUser.GENDER.MALE))).collect(Collectors.toList());
//        return ret;
//
//
//    }
//
//    @Override
//    public TestUser findUserByName(String username) {
//        TestUser user = jdbcOperations.queryForObject("SELECT id,username,password,enabled FROM test_user", (rs, i)->{
//            TestUser u = new TestUser();
//            u.setId(rs.getLong(1));
//            u.setUsername(rs.getString(2));
//            u.setPassword(rs.getString(3));
//            u.setGender(TestUser.GENDER.FEMALE);
//            u.setEnabled(rs.getBoolean(4));
//            return u;
//        });
//        return user;
//    }
//
//    @Override
//    public void testMap(Map<String, Object> params) {
//        System.out.println(String.format("传入参数:%s", params));
//    }
//}
