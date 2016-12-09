package com.tt.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "b_inspect_scheme")
//@JsonIgnoreProperties(value = {"dept" })

public class InspectScheme extends BaseModel{
    private Integer id;
    private String name;
    @JsonIgnoreProperties(value = {"children" })
    private Project project;
    private Byte basement_lev;
    private Byte safety_lev;
    private Integer pile_count;

    private File approval_file;
    private File inspect_file;
    private InspectItem inspectItem;
    @JsonIgnoreProperties(value = {"user","majorUser","assistantUser" ,"project" })
    private List<InspectPlan> children;


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
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne
    @JoinColumn(name = "inspect_project_id")
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Basic
    @Column(name = "basement_lev")
    public Byte getBasement_lev() {
        return basement_lev;
    }

    public void setBasement_lev(Byte basement_lev) {
        this.basement_lev = basement_lev;
    }

    @Basic
    @Column(name = "safety_lev")
    public Byte getSafety_lev() {
        return safety_lev;
    }

    public void setSafety_lev(Byte safety_lev) {
        this.safety_lev = safety_lev;
    }

    @Basic
    @Column(name = "pile_count")
    public Integer getPile_count() {
        return pile_count;
    }

    public void setPile_count(Integer pile_count) {
        this.pile_count = pile_count;
    }

    @OneToOne
    @JoinColumn(name = "approval_file_id")
    public File getApproval_file() {
        return approval_file;
    }

    public void setApproval_file(File approval_file) {
        this.approval_file = approval_file;
    }

    @OneToOne
    @JoinColumn(name = "inspect_file_id")
    public File getInspect_file() {
        return inspect_file;
    }

    public void setInspect_file(File inspect_file) {
        this.inspect_file = inspect_file;
    }

    @ManyToOne
    @JoinTable(name="b_r_inspect_scheme_item",joinColumns = @JoinColumn(name="inspect_scheme_id"),inverseJoinColumns=@JoinColumn(name="inspect_item_id"))
    public InspectItem getInspectItem() {
        return inspectItem;
    }

    public void setInspectItem(InspectItem inspectItem) {
        this.inspectItem = inspectItem;
    }

    @OneToMany(mappedBy = "inspectScheme",fetch = FetchType.EAGER)
//    @OrderColumn(name = "id")
    public List<InspectPlan> getChildren() {
        return children;
    }

    public void setChildren(List<InspectPlan> children) {
        this.children = children;
    }
    @Transient
    public String getText(){
        return this.name;
    }

    @Transient
    public int getLevel() {
        return 1;
    }

}
