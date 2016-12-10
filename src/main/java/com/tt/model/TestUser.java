//package com.tt.model;
//
//import javax.persistence.*;
//import java.util.List;
//
///**
// * Created by tt on 2016/11/21.
// */
//@Entity
//@Table(name = "test_user")
//public class TestUser {
//    public enum GENDER{
//        MALE,
//        FEMALE
//    }
//    @Id
//    @GeneratedValue
//    private Long id;
//    private String username;
//    private GENDER gender;
//    private String password;
//    private boolean enabled;
//    @OneToMany(mappedBy = "user")
//    @OrderColumn(name = "id")
//    private List<TestBook> books;
//
//    public TestUser() {
//    }
//
//    public TestUser(Long id, String name, GENDER gender) {
//        this.id = id;
//        this.username = name;
//        this.gender = gender;
//    }
//
//    public TestUser(Long id, String username, GENDER gender, String password, boolean enabled) {
//        this.id = id;
//        this.username = username;
//        this.gender = gender;
//        this.password = password;
//        this.enabled = enabled;
//    }
//
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public GENDER getGender() {
//        return gender;
//    }
//
//    public void setGender(GENDER gender) {
//        this.gender = gender;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public boolean isEnabled() {
//        return enabled;
//    }
//
//    public void setEnabled(boolean enabled) {
//        this.enabled = enabled;
//    }
//
//
//    public List<TestBook> getBooks() {
//        return books;
//    }
//
//    public void setBooks(List<TestBook> books) {
//        this.books = books;
//    }
//
//    @Override
//    public String toString() {
//        return "Inspector{" +
//                "id=" + id +
//                ", username='" + username + '\'' +
//                ", gender=" + gender +
//                ", password='" + password + '\'' +
//                ", enabled=" + enabled +
//                ", books=" + books +
//                '}';
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        TestUser user = (TestUser) o;
//
//        return id != null ? id.equals(user.id) : user.id == null;
//
//    }
//
//    @Override
//    public int hashCode() {
//        return id != null ? id.hashCode() : 0;
//    }
//}
