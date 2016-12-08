package com.tt.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "b_inspect_plan")
@JsonIgnoreProperties(value = {"dept"})
public class InspectPlan extends BaseModel {
    private Integer id;
    private String name;
    @JsonIgnoreProperties(value = {"project","basement_lev","safety_lev","pile_count","dept","approval_file","inspect_file","inspectItem","children","isDeleted"})
    private InspectScheme inspectScheme;
    //    private List<InspectMethod> inspectMethods;
    private User user;
    private Equipment equipment;
    private String stzh;
    private Date start_time;
    private Date end_time;
    private User majorUser;
    private User assistantUser;
    private String note;
    @JsonIgnoreProperties(value = {"name","province","city","address","lat","lng","constructor","builder","user","note","status","children"})
    private Project project;
    private List<InspectMethod> inspectMethods;

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
    @JoinColumn(name = "inspect_scheme_id")
    public InspectScheme getInspectScheme() {
        return inspectScheme;
    }

    public void setInspectScheme(InspectScheme inspectScheme) {
        this.inspectScheme = inspectScheme;
    }

    @ManyToOne
    @JoinColumn(name = "project_id")
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }


    //    @ManyToMany
//    @JoinTable(name = "b_r_inspect_plan_method", joinColumns = @JoinColumn(name = "plan_id"), inverseJoinColumns = @JoinColumn(name = "method_id"))
//    public List<InspectMethod> getInspectMethods() {
//        return inspectMethods;
//    }
//
//    public void setInspectMethods(List<InspectMethod> inspectMethods) {
//        this.inspectMethods = inspectMethods;
//    }

    @ManyToOne
    @JoinColumn(name = "inspector_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "equipment_id")
    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    @Basic
    @Column(name = "start_time")
    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    @Basic
    @Column(name = "end_time")
    public Date getEnd_time() {
        return end_time;
    }


    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    @ManyToOne
    @JoinColumn(name = "major_inspector_id")
    public User getMajorUser() {
        return majorUser;
    }

    public void setMajorUser(User majorUser) {
        this.majorUser = majorUser;
    }

    @ManyToOne
    @JoinColumn(name = "assistant_inspector_id")
    public User getAssistantUser() {
        return assistantUser;
    }

    public void setAssistantUser(User assistantUser) {
        this.assistantUser = assistantUser;
    }

    @Basic
    @Column(name = "note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Transient
    public String getText() {
        return this.name;
    }

    @Transient
    public int getLevel() {
        return 2;
    }

    @Basic
    @Column(name = "stzh")
    public String getStzh() {
        return stzh;
    }

    public void setStzh(String stzh) {
        this.stzh = stzh;
    }

    @ManyToMany
    @JoinTable(name="b_r_inspect_plan_method",joinColumns = @JoinColumn(name="plan_id"),inverseJoinColumns=@JoinColumn(name="method_id"))
    public List<InspectMethod> getInspectMethods() {
        return inspectMethods;
    }

    public void setInspectMethods(List<InspectMethod> inspectMethods) {
        this.inspectMethods = inspectMethods;
    }
}
