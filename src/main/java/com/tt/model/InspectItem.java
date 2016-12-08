package com.tt.model;



import javax.persistence.*;

@Entity
@Table(name="b_inspect_item")

public class InspectItem  extends BaseModel{
  private Integer id;
  private String name;

  @Id
  @Column(name="id")
  @GeneratedValue
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Basic
  @Column(name="name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
