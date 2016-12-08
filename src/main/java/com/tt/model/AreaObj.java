package com.tt.model;

import javax.persistence.*;

@Entity
@Table(name = "b_area")
public class AreaObj extends BaseModel{
    private Integer id;
    private String text;
    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
