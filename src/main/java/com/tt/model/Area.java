package com.tt.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "b_area")

public class Area extends BaseModel{
    private Integer id;
    private String text;
    private Byte level;
    private Area parent;
    private String note;
    private List<Area> children = new LinkedList<>();

    @Id
    @Column(name = "id")
    @GeneratedValue
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

    @Basic
    @Column(name = "level")
    public Byte getLevel() {
        return level;
    }

    public void setLevel(Byte level) {
        this.level = level;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pid")
    @JsonIgnoreProperties(value = { "parent", "children" })
    public Area getParent() {
        return parent;
    }

    public void setParent(Area parent) {
        this.parent = parent;
    }


    @Basic
    @Column(name = "note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    /**
     *
     * @return
     */
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "parent",cascade = {CascadeType.REMOVE,CascadeType.DETACH})  // TODO 需要加上mappedBy,否则报错，为啥不知道，待查
    public List<Area> getChildren() {
        return children;
    }

    public void setChildren(List<Area> children) {
        this.children = children;
    }
}
