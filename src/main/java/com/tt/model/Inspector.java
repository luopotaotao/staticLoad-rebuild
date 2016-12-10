package com.tt.model;

import javax.persistence.*;

/**
 * Created by tt on 2016/12/7.
 */
@Entity
@Table(name = "my_user_details")
public class Inspector extends BaseModel {
    @Id
    @GeneratedValue
    private Integer id;
    private String username;
    private String realName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
