/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ps.bean;

import com.ps.entity.OrderContent;
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
public class OrderContentBean {

    DbBean db = null;
    PreparedStatement preparedstatement = null;
    private static Logger logger = null;

    public OrderContentBean() {
        BasicConfigurator.configure();
        logger = Logger.getLogger(OrderContentBean.class);
    }

    //查詢所有類型
    public List getOrderContent() {

        db = new DbBean();
        List type = null;
        try {
            ResultSet rs = db.search("select * from ordercontent");
            type = new ArrayList();
            while (rs.next()) {
                OrderContent oc = new OrderContent();
                oc.setListid(rs.getInt("listid"));
                oc.setProduct(rs.getInt("product"));
                oc.setQuantity(rs.getInt("quantity"));
                type.add(oc);
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

    public boolean insertOrderContent(int Listid, List ordercontent) {
        db = new DbBean();
        boolean b = false;
        OrderContent oc = null;
        try {
            db.conn.setAutoCommit(false);
            Iterator it = ordercontent.iterator();
            //先清空訂單內容
            preparedstatement = db.conn.prepareStatement("delete from ordercontent where listid=?");
            preparedstatement.setInt(1, Listid);
            preparedstatement.executeUpdate();
            //更新
            preparedstatement = db.conn.prepareStatement("insert into ordercontent(listid,product,quantity) values (?,?,?)");
            while (it.hasNext()) {
                oc = (OrderContent) it.next();
                preparedstatement.setInt(1, Listid);
                preparedstatement.setInt(2, oc.getProduct());
                preparedstatement.setInt(3, oc.getQuantity());
                preparedstatement.addBatch();
            }
            preparedstatement.executeBatch();
            db.conn.commit();
            b = true;
        } catch (SQLException ex) {
            logger.error(ex);
            try {
                db.conn.rollback();
            } catch (SQLException ex1) {
                logger.error(ex1);
            }
        } finally {
            try {
                db.conn.setAutoCommit(true);
                preparedstatement.close();
                db.conn.close();
            } catch (SQLException ex) {
                logger.error(ex);
            }
        }
        return b;
    }

    public boolean updateOrderContent(List ordercontent) {
        db = new DbBean();
        boolean b = false;
        OrderContent oc = null;
        try {
            db.conn.setAutoCommit(false);
            Iterator it = ordercontent.iterator();
            preparedstatement = db.conn.prepareStatement("update ordercontent set quantity=? where id=?");
            while (it.hasNext()) {
                oc = (OrderContent) it.next();
                preparedstatement.setInt(1, oc.getQuantity());
                preparedstatement.setInt(2, oc.getId());
                preparedstatement.addBatch();
            }
            preparedstatement.executeBatch();
            db.conn.commit();
            b = true;
        } catch (SQLException ex) {
            logger.error(ex);
            try {
                db.conn.rollback();
            } catch (SQLException ex1) {
                logger.error(ex1);
            }
        } finally {
            try {
                db.conn.setAutoCommit(true);
                preparedstatement.close();
                db.conn.close();
            } catch (SQLException ex) {
                logger.error(ex);
            }
        }
        return b;
    }

    //訂單內容商品退簽
    public boolean deleteOrderlistContent(List product) {
        db = new DbBean();
        boolean b = false;
        try {
            Iterator it = product.iterator();
            preparedstatement = db.conn.prepareStatement("delete from ordercontent where product=?");
            db.conn.setAutoCommit(false);
            while (it.hasNext()) {
                preparedstatement.setString(1, (String) it.next());
                preparedstatement.addBatch();
            }
            preparedstatement.executeBatch();
            db.conn.commit();
            b = true;
        } catch (Exception ex) {
            logger.error(ex);
        } finally {
            try {
                db.conn.setAutoCommit(true);
                preparedstatement.close();
                db.conn.close();
            } catch (SQLException ex) {
                logger.error(ex);
            }
        }
        return b;
    }
}
