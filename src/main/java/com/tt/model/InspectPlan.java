package com.tt.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

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
    private Inspector inspector;
    private Equipment equipment;
    private String stzh;
    private Date start_time;
    private Date end_time;
    private Inspector majorInspector;
    private Inspector assistantInspector;
    private InspectMethod inspectMethod;
    private String note;
    private Double maxLoad;
    private Double maxOffset;

    @JsonIgnoreProperties(value = {"name","province","city","address","lat","lng","constructor","builder","inspector","note","status","children"})
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
    public Inspector getInspector() {
        return inspector;
    }

    public void setInspector(Inspector inspector) {
        this.inspector = inspector;
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
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    @Basic
    @Column(name = "end_time")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    public Date getEnd_time() {
        return end_time;
    }


    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    @ManyToOne
    @JoinColumn(name = "major_inspector_id")
    public Inspector getMajorInspector() {
        return majorInspector;
    }

    public void setMajorInspector(Inspector majorInspector) {
        this.majorInspector = majorInspector;
    }

    @ManyToOne
    @JoinColumn(name = "assistant_inspector_id")
    public Inspector getAssistantInspector() {
        return assistantInspector;
    }

    public void setAssistantInspector(Inspector assistantInspector) {
        this.assistantInspector = assistantInspector;
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

    @Transient
    public Double getMaxLoad() {
        return maxLoad;
    }

    public void setMaxLoad(Double maxLoad) {
        this.maxLoad = maxLoad;
    }
    @Transient
    public Double getMaxOffset() {
        return maxOffset;
    }

    public void setMaxOffset(Double maxOffset) {
        this.maxOffset = maxOffset;
    }

    @ManyToOne
    @JoinColumn(name = "inspect_method_id")
    public InspectMethod getInspectMethod() {
        return inspectMethod;
    }

    public void setInspectMethod(InspectMethod inspectMethod) {
        this.inspectMethod = inspectMethod;
    }
}
