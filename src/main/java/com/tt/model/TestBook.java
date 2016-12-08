package com.tt.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by tt on 2016/12/3.
 */
@Entity
@Table(name = "book")
public class TestBook {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    @OneToMany(mappedBy = "book")
    @OrderColumn(name = "id")
    private List<TestPublisher> publishers;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private TestUser user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<TestPublisher> getPublishers() {
        return publishers;
    }

    public void setPublishers(List<TestPublisher> publishers) {
        this.publishers = publishers;
    }

    public TestUser getUser() {
        return user;
    }


    public void setUser(TestUser user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", publishers=" + publishers +
                '}';
    }
}
