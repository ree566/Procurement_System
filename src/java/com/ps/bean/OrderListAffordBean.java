/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ps.bean;

import com.ps.entity.Afford_View;
import com.ps.entity.OrderListAfford;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 *
 * @author Wei.Cheng
 */
public class OrderListAffordBean implements Serializable {

    private DbBean db = null;
    private PreparedStatement preparedstatement = null;
    private static Logger logger = null;

    public OrderListAffordBean() {
        BasicConfigurator.configure();
        logger = Logger.getLogger(OrderListAffordBean.class);
    }

    public List getOrderListAfford() {
       return getListAfford("select * from orderlistafford");
    }
    
    public List getOrderListAffordLog(){
        return getListAfford("select * from afford_log");
    }
    
    private List getListAfford(String sql){
        db = new DbBean();
        List olalist = null;
        try {
            ResultSet rs = db.search(sql);
            olalist = new ArrayList();
            while (rs.next()) {
                OrderListAfford ola = new OrderListAfford();
                ola.setId(rs.getInt("id"));
                ola.setListid(rs.getInt("listid"));
                ola.setUnit(rs.getString("unit"));
                ola.setAff_percent(rs.getDouble("aff_percent"));
                olalist.add(ola);
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
        return olalist;
    }

    public List getAffordTotal() {
        db = new DbBean();
        Map map = new TreeMap();
        List l = new ArrayList();
        try {
            ResultSet rs = db.search("select * from afford_View");
            while (rs.next()) {
                Afford_View av = new Afford_View();
                av.setListid(rs.getInt("listid"));
                av.setAff_percent_total(rs.getInt("aff_percent_total"));
                l.add(av);
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
        return l;
    }

    //新增
    public boolean insertOrderListAfford(List alf) {
        String sql = "insert into orderlistafford(listid,unit,aff_percent) values (?,?,?)";
        boolean b = alterOrderListAfford(alf, sql, 1);
        return b;
    }

    //更新
    public boolean updateOrderListAfford(List alf) {
        String sql = "update orderlistafford set unit = ?,aff_percent=? where id = ?";
        boolean b = alterOrderListAfford(alf, sql, 0);
        return b;
    }

    //刪除
    public boolean deleteOrderListAfford(List alf) {
        String sql = "delete from orderlistafford where id = ?";
        boolean b = alterOrderListAfford(alf, sql, 2);
        return b;
    }

    private boolean alterOrderListAfford(List orderlistafford, String sql, int type) {
        db = new DbBean();
        boolean b = false;
        try {
            db.conn.setAutoCommit(false);
            Iterator it = orderlistafford.iterator();
            preparedstatement = db.conn.prepareStatement(sql);
            while (it.hasNext()) {
                OrderListAfford ola = (OrderListAfford) it.next();
                switch (type) {
                    case 0:
                        preparedstatement.setString(1, ola.getUnit().toUpperCase());
                        preparedstatement.setDouble(2, ola.getAff_percent());
                        preparedstatement.setInt(3, ola.getListid());
                        break;
                    case 1:
                        preparedstatement.setInt(1, ola.getListid());
                        preparedstatement.setString(2, ola.getUnit().toUpperCase());
                        preparedstatement.setDouble(3, ola.getAff_percent());
                        break;
                    case 2:
                        preparedstatement.setInt(1, ola.getId());
                        break;
                }
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
}
