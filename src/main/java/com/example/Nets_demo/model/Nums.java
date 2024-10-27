package com.example.Nets_demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Numbers")
public class Nums {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  private Integer number;

  public Nums() {}

  public Nums(long id, Integer number) {
    this.id = id;
    this.number = number;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Integer getNumber() {
    return number;
  }

  public void setNumber(Integer number) {
    this.number = number;
  }
}
