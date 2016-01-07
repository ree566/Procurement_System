/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ps.bean;

import com.ps.entity.Unit;
import java.sql.PreparedStatement;
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
public class UnitBean {
    DbBean db = null;
    PreparedStatement preparedstatement = null;
    private static Logger logger = null;

    public UnitBean() {
        BasicConfigurator.configure();
        logger = Logger.getLogger(UnitBean.class);
    }
      //查詢所有類型
    public List getUnit() {

        db = new DbBean();
        List type = null;
        try {
            ResultSet rs = db.search("select * from unit");
            type = new ArrayList();
            while (rs.next()) {
                Unit u = new Unit();
                u.setName(rs.getString("name"));
                u.setId(rs.getInt("id"));
                type.add(u);
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

    public List getOneUnit(String getid) {
        int id = 0;
        if(getid != null){
            id = Integer.parseInt(getid);
        }
        return getOneUnit(id);
    }
    
    //查詢單筆類型
    public List getOneUnit(int id) {
        List list = getUnit();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Unit unitlist = (Unit) it.next();
            if (unitlist.getId() != id) {
                it.remove();
            }
        }
        return list;
    }

    //新增類型
    public boolean insertUnit(List unit) {
        String sql = "insert into unit(name) values (?)";
        boolean b = alterUnit(unit, sql, 0);
        return b;
    }

    //修改類型
    public boolean updateUnit(List unit) {
        String sql = "update unit set name=? where id=?";
        boolean b = alterUnit(unit, sql, 1);
        return b;
    }

    //刪除類型
    public boolean deleteUnit(List unit_num) {
        String sql = "delete from unit where id=?";
        boolean b = alterUnit(unit_num, sql, 2);
        return b;
    }

    @SuppressWarnings("null")
    public boolean alterUnit(List unit, String sql, int type) {
        db = new DbBean();
        boolean b = false;
        Unit unitlist = null;
        try {
            db.conn.setAutoCommit(false);
            Iterator it = unit.iterator();
            preparedstatement = db.conn.prepareStatement(sql);
            while (it.hasNext()) {
                if (type < 2) {
                    unitlist = (Unit) it.next();
                }
                switch (type) {
                    case 0:
                        preparedstatement.setString(1, unitlist.getName());
                        break;
                    case 1:
                        preparedstatement.setString(1, unitlist.getName());
                        preparedstatement.setInt(2, unitlist.getId());
                        break;
                    case 2:
                        preparedstatement.setString(1, (String) it.next());
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
