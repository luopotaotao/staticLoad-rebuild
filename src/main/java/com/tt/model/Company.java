package com.tt.model;

import javax.persistence.*;

/**
 * Created by taotao on 2016/9/23.
 */
@Entity
@Table(name = "b_company")
public class Company  extends BaseModel implements java.io.Serializable{
    private Integer id;
    private String name;
    private String contacts;
    private String tel;
    private Byte typ;

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

    @Basic
    @Column(name = "contacts")
    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    @Basic
    @Column(name = "tel")
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Basic
    @Column(name = "typ")
    public Byte getTyp() {
        return typ;
    }

    public void setTyp(Byte typ) {
        this.typ = typ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company bCompany = (Company) o;

        if (id != bCompany.id) return false;
        if (name != null ? !name.equals(bCompany.name) : bCompany.name != null) return false;
        if (contacts != null ? !contacts.equals(bCompany.contacts) : bCompany.contacts != null) return false;
        if (tel != null ? !tel.equals(bCompany.tel) : bCompany.tel != null) return false;
        if (typ != null ? !typ.equals(bCompany.typ) : bCompany.typ != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (contacts != null ? contacts.hashCode() : 0);
        result = 31 * result + (tel != null ? tel.hashCode() : 0);
        result = 31 * result + (typ != null ? typ.hashCode() : 0);
        return result;
    }
}
