package com.tt.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by tt on 2016/12/11.
 */
@Entity
@Table(name = "b_project")
public class OverViewProject extends BaseModel{
    private Integer id;
    private String code;
    private String name;
    private AreaObj province;
    private AreaObj city;
    private String address;
    private Float lat;
    private Float lng;
    private String note;
    private Integer status;

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
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "lat")
    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    @Basic
    @Column(name = "lng")
    public Float getLng() {
        return lng;
    }

    public void setLng(Float lng) {
        this.lng = lng;
    }

    @ManyToOne
    @JoinColumn(name = "province_id")
    public AreaObj getProvince() {
        return province;
    }

    public void setProvince(AreaObj province) {
        this.province = province;
    }

    @ManyToOne
    @JoinColumn(name = "city_id")
    public AreaObj getCity() {
        return city;
    }

    public void setCity(AreaObj city) {
        this.city = city;
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
        return 0;
    }

    @Transient
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
