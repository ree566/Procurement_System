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
@Table(name = "orderlist_history_view")
public class Orderlist_History_View implements Serializable {

    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private int id;
    @Column(name = "o_date")
    private String o_date;
    @Column(name = "a_id")
    private int a_id;
    @Column(name = "name")
    private String name;
    @Column(name = "memo")
    private String memo;
    @Column(name = "product")
    private String product;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "price")
    private int price;
    @Column(name = "aUnit")
    private String aUnit;
    @Column(name = "total")
    private int total;
    @Column(name = "company")
    private String company;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getO_date() {
        return o_date;
    }

    public void setO_date(String o_date) {
        this.o_date = o_date;
    }

    public int getA_id() {
        return a_id;
    }

    public void setA_id(int a_id) {
        this.a_id = a_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

//    public float getDiscount() {
//        return discount;
//    }
//
//    public void setDiscount(float discount) {
//        this.discount = discount;
//    }
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getaUnit() {
        return aUnit;
    }

    public void setaUnit(String aUnit) {
        this.aUnit = aUnit;
    }

}
