package com.tt.model;

import javax.persistence.*;

@Entity
@Table(name = "b_inspect_method")
public class InspectMethod extends BaseModel {
    private Integer id;
    private String name;
    private Integer inspect_item_id;

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
    @Column(name = "inspect_item_id")
    public Integer getInspect_item_id() {
        return inspect_item_id;
    }

    public void setInspect_item_id(Integer inspect_item_id) {
        this.inspect_item_id = inspect_item_id;
    }
}
