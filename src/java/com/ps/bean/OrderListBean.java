/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ps.bean;

import com.ps.entity.OrderContent;
import com.ps.entity.OrderList;
import com.ps.entity.OrderListAfford;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 *
 * @author Wei.Cheng
 */
public class OrderListBean {

    DbBean db = null;
    PreparedStatement preparedstatement = null;
    private static Logger logger = null;
    SimpleDateFormat sdf = null;
    String date = null;

    public OrderListBean() {
        BasicConfigurator.configure();
        logger = Logger.getLogger(OrderListBean.class);
        sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        date = sdf.format(new Date());
    }

    //查詢所有訂單
    public List getOrderList() {

        db = new DbBean();
        List type = null;
        try {
            ResultSet rs = db.search("select * from orderlist order by id desc");
            type = new ArrayList();
            while (rs.next()) {
                OrderList ol = new OrderList();
                ol.setId(rs.getInt("id"));
                ol.setO_date(rs.getString("o_date"));
                ol.setApplicant(rs.getInt("applicant"));
                ol.setMemo(rs.getString("memo"));
                ol.setStatus(rs.getInt("status"));
                type.add(ol);
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

    public Map getOrderListTotal() {
        db = new DbBean();
        Map map = new HashMap();
        try {
            ResultSet rs = db.search("select * from finaltotal");
            while (rs.next()) {
                map.put(rs.getInt("listid"), rs.getInt("total"));
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
        return map;
    }

    //修改訂單
    public boolean updateMemo(int id, String memo) {
        db = new DbBean();
        boolean b = false;
        try {
            db.conn.setAutoCommit(false);
            preparedstatement = db.conn.prepareStatement("update orderlist set memo=? where id=?");
            preparedstatement.setString(1, memo);
            preparedstatement.setInt(2, id);
            preparedstatement.executeUpdate();
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

    public boolean updateStatus(int status, int id, String sql) {
        db = new DbBean();
        boolean b = false;
        try {
            db.conn.setAutoCommit(false);
            preparedstatement = db.conn.prepareStatement(sql);
            if (id != -1) {
                preparedstatement.setInt(1, status);
                preparedstatement.setInt(2, id);
            }
            preparedstatement.executeUpdate();
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

    public boolean updateStatus(int status, int id) {
        String sql = "update orderlist set status=? where id=?";
        if (id == -1) {
            if (status == 5) {
                sql = "update orderlist set status=4 where status=0";
            } else {
                sql = "update orderlist set status=0 where status=4";
            }
        }
        return updateStatus(status, id, sql);
    }
//
    //刪除訂單

    public boolean deleteOrderList(Set<Integer> orderlistid) {
        boolean b = false;
        db = new DbBean();
        try {
            for (int id : orderlistid) {
                db.conn.setAutoCommit(false);
                preparedstatement = db.conn.prepareStatement("delete from ordercontent where listid=?");
                preparedstatement.setInt(1, id);
                preparedstatement.addBatch();
                preparedstatement.executeBatch();
                //-----------------------------------------------------------------------------
                preparedstatement = db.conn.prepareStatement("delete from orderlistafford where listid=?");
                preparedstatement.setInt(1, (int) id);
                preparedstatement.addBatch();
                preparedstatement.executeBatch();
                //-----------------------------------------------------------------------------
                preparedstatement = db.conn.prepareStatement("delete from orderlist where id=?");
                preparedstatement.setInt(1, (int) id);
                preparedstatement.addBatch();
            }
            preparedstatement.executeBatch();
            //-----------------------------------------------------------------------------
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

    //新增訂單(先新增訂單再新增訂單內容)
    @SuppressWarnings("null")
    public boolean insertOrderList(List orderlist, List ordercontent, List orderlistafford) {
        db = new DbBean();
        boolean b = false;
        OrderList ollist = null;
        OrderContent oclist = null;
        OrderListAfford olf = null;
        int orderlistid = 0;
        try {
            db.conn.setAutoCommit(false);
            Iterator it = orderlist.iterator();
            preparedstatement = db.conn.prepareStatement("insert into orderlist(o_date,applicant,memo,status) values (?,?,?,?)");
            while (it.hasNext()) {
                ollist = (OrderList) it.next();
                preparedstatement.setString(1, date);
                preparedstatement.setInt(2, ollist.getApplicant());
                preparedstatement.setString(3, ollist.getMemo());
                preparedstatement.setInt(4, 0);
                preparedstatement.addBatch();
            }
            preparedstatement.executeBatch();
            //-----------------------------------------------------------------------------------
            ResultSet rs = db.search("select MAX(id) id from orderlist");//取得此次新增的訂單編號
            while (rs.next()) {
                orderlistid = rs.getInt("id");
            }
            it = ordercontent.iterator();
            preparedstatement = db.conn.prepareStatement("insert into ordercontent(listid,product,quantity) values (?,?,?)");
            while (it.hasNext()) {
                oclist = (OrderContent) it.next();
                preparedstatement.setInt(1, orderlistid);
                preparedstatement.setInt(2, oclist.getProduct());
                preparedstatement.setInt(3, oclist.getQuantity());
                preparedstatement.addBatch();
            }
            preparedstatement.executeBatch();
            //-----------------------------------------------------------------------------------
            it = orderlistafford.iterator();
            preparedstatement = db.conn.prepareStatement("insert into orderlistafford(listid,unit,aff_percent) values (?,?,?)");
            while (it.hasNext()) {
                olf = (OrderListAfford) it.next();
                preparedstatement.setInt(1, orderlistid);
                preparedstatement.setString(2, olf.getUnit());
                preparedstatement.setDouble(3, olf.getAff_percent());
                preparedstatement.addBatch();
            }
            preparedstatement.executeBatch();
            //-----------------------------------------------------------------------------------
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
