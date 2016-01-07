/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ps.bean;

import com.ps.entity.Company;
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
public class CompanyBean {
    DbBean db = null;
    PreparedStatement preparedstatement = null;
    private static Logger logger = null;

    public CompanyBean() {
        BasicConfigurator.configure();
        logger = Logger.getLogger(CompanyBean.class);
    }
      //查詢所有類型
    public List getCompany() {

        db = new DbBean();
        List l = null;
        try {
            ResultSet rs = db.search("select * from company");
            l = new ArrayList();
            while (rs.next()) {
                Company u = new Company();
                u.setName(rs.getString("name"));
                u.setId(rs.getInt("id"));
                l.add(u);
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

    //新增類型
    public boolean insertCompany(List company) {
        String sql = "insert into company(name) values (?)";
        boolean b = alterCompany(company, sql, 0);
        return b;
    }

    //修改類型
    public boolean updateCompany(List company) {
        String sql = "update company set name=? where id=?";
        boolean b = alterCompany(company, sql, 1);
        return b;
    }

    //刪除類型
    public boolean deleteCompany(List company_num) {
        String sql = "delete from company where id=?";
        boolean b = alterCompany(company_num, sql, 2);
        return b;
    }

    @SuppressWarnings("null")
    public boolean alterCompany(List company, String sql, int type) {
        db = new DbBean();
        boolean b = false;
        Company companylist = null;
        try {
            db.conn.setAutoCommit(false);
            Iterator it = company.iterator();
            preparedstatement = db.conn.prepareStatement(sql);
            while (it.hasNext()) {
                if (type < 2) {
                    companylist = (Company) it.next();
                }
                switch (type) {
                    case 0:
                        preparedstatement.setString(1, companylist.getName());
                        break;
                    case 1:
                        preparedstatement.setString(1, companylist.getName());
                        preparedstatement.setInt(2, companylist.getId());
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
