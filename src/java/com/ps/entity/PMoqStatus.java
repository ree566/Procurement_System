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
@Table(name = "p_moq_status")
public class PMoqStatus implements Serializable {

    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private int id;
    @Column(name = "product")
    private String product;
    @Column(name = "quantity_total")
    private int quantity_total;
    @Column(name = "p_moq")
     private int pid;
    @Column(name = "pid")
    private int p_moq;
    @Column(name = "diff")
    private int diff;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getQuantity_total() {
        return quantity_total;
    }

    public void setQuantity_total(int quantity_total) {
        this.quantity_total = quantity_total;
    }

    public int getP_moq() {
        return p_moq;
    }

    public void setP_moq(int p_moq) {
        this.p_moq = p_moq;
    }

    public int getDiff() {
        return diff;
    }

    public void setDiff(int diff) {
        this.diff = diff;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

}
