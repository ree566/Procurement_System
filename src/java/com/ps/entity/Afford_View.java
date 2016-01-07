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
@Table(name = "accounting")
public class Afford_View implements Serializable{

    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private int id;
    @Column(name = "listid")
    private int listid;
    @Column(name = "aff_percent_total")
    private int aff_percent_total;

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

    public int getAff_percent_total() {
        return aff_percent_total;
    }

    public void setAff_percent_total(int aff_percent_total) {
        this.aff_percent_total = aff_percent_total;
    }

}
