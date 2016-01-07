/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ps.bean;

import com.ps.entity.Catagorized;
import java.io.Serializable;
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
public class CatagorizedBean implements Serializable {

    DbBean db = null;
    PreparedStatement preparedstatement = null;
    private static Logger logger = null;

    public CatagorizedBean() {
        BasicConfigurator.configure();
        logger = Logger.getLogger(CatagorizedBean.class);
    }

    //查詢所有類型
    public List getCatagorized() {

        db = new DbBean();
        List type = null;
        try {
            ResultSet rs = db.search("select * from catagorized");
            type = new ArrayList();
            while (rs.next()) {
                Catagorized cata = new Catagorized();
                cata.setName(rs.getString("name"));
                cata.setId(rs.getInt("id"));
                type.add(cata);
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

    public List getOneCatagorized(String getid) {
        int id = 0;
        if (getid != null) {
            id = Integer.parseInt(getid);
        }
        return getOneCatagorized(id);
    }

    //查詢單筆類型
    public List getOneCatagorized(int id) {
        List list = getCatagorized();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Catagorized catagorizedlist = (Catagorized) it.next();
            if (catagorizedlist.getId() != id) {
                it.remove();
            }
        }
        return list;
    }

    //新增類型
    public boolean insertCatagorized(List catagorized) {
        String sql = "insert into catagorized(name) values (?)";
        boolean b = alterCatagorized(catagorized, sql, 0);
        return b;
    }

    //修改類型
    public boolean updateCatagorized(List catagorized) {
        String sql = "update catagorized set name=? where id=?";
        boolean b = alterCatagorized(catagorized, sql, 1);
        return b;
    }

    //刪除類型
    public boolean deleteCatagorized(List catagorized_num) {
        String sql = "delete from catagorized where id=?";
        boolean b = alterCatagorized(catagorized_num, sql, 2);
        return b;
    }

    public boolean alterCatagorized(List catagorized, String sql, int type) {
        db = new DbBean();
        boolean b = false;
        Catagorized catalist = null;
        try {
            db.conn.setAutoCommit(false);
            Iterator it = catagorized.iterator();
            preparedstatement = db.conn.prepareStatement(sql);
            while (it.hasNext()) {
                if (type < 2) {
                    catalist = (Catagorized) it.next();
                }
                switch (type) {
                    case 0:
                        preparedstatement.setString(1, catalist.getName());
                        break;
                    case 1:
                        preparedstatement.setString(1, catalist.getName());
                        preparedstatement.setInt(2, catalist.getId());
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
