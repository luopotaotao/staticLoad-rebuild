package com.tt.model;

import org.hibernate.annotations.Where;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Created by tt on 2016/10/17.
 */
@MappedSuperclass
@Where(clause = "deleted=false")
public class BaseModel {
    private Integer dept_id;
    private boolean isDeleted;

    @Basic
    @Column(name = "dept_id")
    public Integer getDept_id() {
        return dept_id;
    }

    public void setDept_id(Integer dept_id) {
        this.dept_id = dept_id;
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
