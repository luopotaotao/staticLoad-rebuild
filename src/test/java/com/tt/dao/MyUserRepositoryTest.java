//package com.tt.dao;
//
//import com.tt.DataBaseConfig;
//import com.tt.RootConfig;
//import com.tt.ext.security.MyUserDetails;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.transaction.annotation.Transactional;
//
///**
// * Created by tt on 2016/12/6.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {RootConfig.class, DataBaseConfig.class})
//@Transactional
//public class MyUserRepositoryTest {
//
//    @Autowired
//    private TestMyUserRepository myUserRepository;
//    @Test
//    public void loadUserByUsername() throws Exception {
//        MyUserDetails userDetails = myUserRepository.loadUserByUsername("root");
//        System.out.println(userDetails);
//    }
//
//}