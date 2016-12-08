package com.tt.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by tt on 2016/10/23.
 */
@Entity
@Table(name = "b_dept")
//@Where(clause = "deleted='false'")
public class Dept_ implements Serializable {
    private Integer id;
    private String name;
    private String logo;
    private String note;
    private boolean isDeleted;

    @Id
    @GeneratedValue
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "logo")
    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Basic
    @Column(name = "note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Basic
    @Column(name = "deleted")
    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
