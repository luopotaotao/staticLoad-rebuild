package com.tt.model;

import javax.persistence.*;

/**
 * Created by tt on 2016/11/28.
 */
@Entity
@Table(name="b_file")
public class File {
    private String uuid;
    private String name;
    private Integer dept_id;

    public File() {
    }

    public File(String uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        this.dept_id = dept_id;
    }

    @Id
    @Column(name = "uuid")
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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
    @Column(name = "dept_id")
    public Integer getDept_id() {
        return dept_id;
    }

    public void setDept_id(Integer dept_id) {
        this.dept_id = dept_id;
    }
}
