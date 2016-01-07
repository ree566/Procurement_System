/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ps.entity;

import java.io.Serializable;
import java.util.Objects;
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
@Table(name = "ordertotal")
public class OrderTotal implements Serializable {

    @Id
    @Basic(optional = false)
    @Column(name = "listid")
    private int listid;
    @Column(name = "l_date")
    private String l_date;
    @Column(name = "applicant")
    private int applicant;
    @Column(name = "aName")
    private String aName;
    @Column(name = "pid")
    private int pid;
    @Column(name = "aMail")
    private String aMail;
    @Column(name = "product")
    private String product;
    @Column(name = "price")
    private int price;
    @Column(name = "l_quantity")
    private int l_quantity;
    @Column(name = "total")
    private int total;
    @Column(name = "p_moq")
    private int p_moq;
    @Column(name = "unit")
    private String unit;
    @Column(name = "listcid")
    private int listcid;
    @Column(name = "company")
    private String company;
    @Column(name = "aUnitNum")
    private int aUnitNum;
    @Column(name = "aUnit")
    private String aUnit;

    public int getaUnitNum() {
        return aUnitNum;
    }

    public void setaUnitNum(int aUnitNum) {
        this.aUnitNum = aUnitNum;
    }

    public String getaUnit() {
        return aUnit;
    }

    public void setaUnit(String aUnit) {
        this.aUnit = aUnit;
    }

    public int getListid() {
        return listid;
    }

    public void setListid(int listid) {
        this.listid = listid;
    }

    public String getL_date() {
        return l_date;
    }

    public void setL_date(String l_date) {
        this.l_date = l_date;
    }

    public int getApplicant() {
        return applicant;
    }

    public void setApplicant(int applicant) {
        this.applicant = applicant;
    }

    public String getaName() {
        return aName;
    }

    public void setaName(String aName) {
        this.aName = aName;
    }

    public String getaMail() {
        return aMail;
    }

    public void setaMail(String aMail) {
        this.aMail = aMail;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getL_quantity() {
        return l_quantity;
    }

    public void setL_quantity(int l_quantity) {
        this.l_quantity = l_quantity;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getP_moq() {
        return p_moq;
    }

    public void setP_moq(int p_moq) {
        this.p_moq = p_moq;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

//    public float getDiscount() {
//        return discount;
//    }
//
//    public void setDiscount(float discount) {
//        this.discount = discount;
//    }
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getListcid() {
        return listcid;
    }

    public void setListcid(int listcid) {
        this.listcid = listcid;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof OrderTotal)) {
            return false;
        }
        OrderTotal ot = (OrderTotal) obj;
        if (Objects.equals(ot.getListid(), this.getListid())) {
            return true;
        }
        return false;
    }

    @Override
    @SuppressWarnings("empty-statement")
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.listid);
        return hash;
    }

}
