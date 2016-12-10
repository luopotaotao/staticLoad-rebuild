//package com.tt.model;
//
//import javax.persistence.*;
//
///**
// * Created by tt on 2016/12/3.
// */
//@Entity
//@Table(name = "publisher")
//public class TestPublisher {
//    @Id
//    @GeneratedValue
//    private Integer id;
//    private String name;
//    @ManyToOne
//    @JoinColumn(name = "book_id")
//    private TestBook book;
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//
//    public TestBook getBook() {
//        return book;
//    }
//
//    public void setBook(TestBook book) {
//        this.book = book;
//    }
//
//    @Override
//    public String toString() {
//        return "Publisher{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                '}';
//    }
//}
