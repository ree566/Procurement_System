/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ps.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Wei.Cheng
 */
@Entity
@Table(name = "orderlistafford")
public class OrderListAfford implements Serializable {

    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private int id;
    @Column(name = "listid")
    private int listid;
    @Column(name = "unit")
    private String unit;
    @Column(name = "aff_percent")
    private double aff_percent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getListid() {
        return listid;
    }

    public void setListid(int listid) {
        this.listid = listid;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getAff_percent() {
        return aff_percent;
    }

    public void setAff_percent(double aff_percent) {
        this.aff_percent = aff_percent;
    }

   
}
