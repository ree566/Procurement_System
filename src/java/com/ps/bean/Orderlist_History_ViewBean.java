/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ps.bean;

import com.ps.entity.Orderlist_History_View;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 *
 * @author Wei.Cheng
 */
public class Orderlist_History_ViewBean implements Serializable {

    private DbBean db = null;
    private static Logger logger = null;

    public Orderlist_History_ViewBean() {
        BasicConfigurator.configure();
        logger = Logger.getLogger(Orderlist_History_ViewBean.class);
    }

    public List getOrderlist_HNumber(int userid) {
        List l = getOrderlist_History_View();
        int i = 0;
        Iterator it = l.iterator();
        while (it.hasNext()) {
            Orderlist_History_View ohv = (Orderlist_History_View) it.next();
            if (ohv.getId() != i) {
                i = ohv.getId();
            } else {
                it.remove();
            }
        }
        Iterator it1 = l.iterator();
        while (it1.hasNext()) {
            Orderlist_History_View ohv = (Orderlist_History_View) it1.next();
            if (userid == 1) {
                return l;
            }
            if (ohv.getA_id() != userid) {
                it1.remove();
            }
        }
        return l;
        //先取出不相同的歷史訂單編號再砍掉不屬於使用者的
    }

    public List getOrderlist_History_View() {
        db = new DbBean();
        List pmslist = null;
        try {
            ResultSet rs = db.search("select * from orderlist_history_view");
            pmslist = new ArrayList();
            while (rs.next()) {
                Orderlist_History_View ohv = new Orderlist_History_View();
                ohv.setId(rs.getInt("id"));
                ohv.setO_date(rs.getString("o_date"));
                ohv.setA_id(rs.getInt("a_id"));
                ohv.setName(rs.getString("name"));
                ohv.setMemo(rs.getString("memo"));
                ohv.setProduct(rs.getString("product"));
                ohv.setQuantity(rs.getInt("quantity"));
                ohv.setPrice(rs.getInt("price"));
                ohv.setaUnit(rs.getString("aUnit"));
                ohv.setCompany(rs.getString("company"));
                ohv.setTotal(rs.getInt("total"));
                pmslist.add(ohv);
            }
        } catch (Exception ex) {
            logger.error(ex);
        } finally {
            try {
                db.result.close();
                db.stat.close();
                db.conn.close();
            } catch (SQLException ex) {
                logger.error(ex);
            }
        }
        return pmslist;
    }
}
