/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ps.bean;

import com.ps.entity.OrderTotal;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 *
 * @author Wei.Cheng
 */
public class OrderTotalBean implements Serializable {

    private DbBean db = null;
    private PreparedStatement preparedstatement = null;
    private static Logger logger = null;

    public OrderTotalBean() {
        BasicConfigurator.configure();
        logger = Logger.getLogger(OrderTotalBean.class);
    }

    public List getOrderTotal() {
        db = new DbBean();
        List otlist = null;
        try {
            ResultSet rs = db.search("select * from ordertotal");
            otlist = new ArrayList();
            while (rs.next()) {
                OrderTotal ot = new OrderTotal();
                ot.setListid(rs.getInt("listid"));
                ot.setL_date(rs.getString("l_date"));
                ot.setApplicant(rs.getInt("applicant"));
                ot.setaName(rs.getString("aName"));
                ot.setaMail(rs.getString("aMail"));
                ot.setProduct(rs.getString("product"));
                ot.setPrice(rs.getInt("price"));
                ot.setL_quantity(rs.getInt("l_quantity"));
                ot.setTotal(rs.getInt("total"));
                ot.setP_moq(rs.getInt("p_moq"));
                ot.setUnit(rs.getString("unit"));
                ot.setCompany(rs.getString("company"));
                ot.setListcid(rs.getInt("listcid"));
                ot.setPid(rs.getInt("pid"));
//                ot.setaUnitNum(rs.getInt("aUnitNum")); 資料庫存在欄位，尚未用到
//                ot.setaUnit(rs.getString("aUnit")); 資料庫存在欄位
                otlist.add(ot);
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
        return otlist;
    }
    
//    public List getOrderTotalByListid(int id){
//        List l = getOrderTotal();
//        Iterator it = l.iterator();
//        while()
//    }
    
     public List getOrderTotalByProduct(Set product) {
        db = new DbBean();
        List type = null;
        StringBuffer sb = new StringBuffer();
        int value = product.size() - 1;
        Iterator it = product.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            if (value > 0) {
                sb.append(",");
                value--;
            }
        }
        try {
            ResultSet rs = db.search("select * from ordertotal where pid in(" + sb + ")");
            type = new ArrayList();
            while (rs.next()) {
                OrderTotal ot = new OrderTotal();
                ot.setListid(rs.getInt("listid"));
                ot.setaName(rs.getString("aName"));
                ot.setPid(rs.getInt("pid"));
                ot.setApplicant(rs.getInt("applicant"));
                ot.setProduct(rs.getString("product"));
                ot.setaMail(rs.getString("aMail"));
                ot.setL_quantity(rs.getInt("l_quantity"));
                type.add(ot);
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
        return type;
    }

}
