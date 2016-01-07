/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ps.bean;

import com.ps.entity.Identit;
import com.ps.md5.MD5Encoding;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 *
 * @author Wei.Cheng
 */
public class IdentitBean implements Serializable {

    private static Logger logger = null;
    private Connection conn = null;

    public IdentitBean() {
        BasicConfigurator.configure();
        logger = Logger.getLogger(IdentitBean.class);
    }

    public List getAllUsers() {
        DbBean db = new DbBean();
        List userlist = null;
        Statement stmt = null;
        try {
            ResultSet rs = db.search("select * from identit");
            userlist = new ArrayList();
            while (rs.next()) {
                Identit i = new Identit();
                i.setId(rs.getInt("id"));
                i.setJobnumber(rs.getString("jobnumber").trim());
                i.setName(rs.getString("name"));
                i.setPassword(rs.getString("password"));
                i.setEmail(rs.getString("email"));
                userlist.add(i);
            }
        } catch (SQLException ex) {
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
        return userlist;
    }

    public Identit login(String username, String password) {
        List l = getAllUsers();
        Iterator it = l.iterator();
        MD5Encoding md5 = new MD5Encoding();
        while (it.hasNext()) {
            Identit i = (Identit) it.next();
            try {
                if (i.getPassword().trim().equals(md5.getMD5Str(username.trim() + password.trim()))) {
                    return i;
                }
            } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                logger.error(ex);
            }
        }
        return null;
    }

//    public List getidentitunit() {
//        DbBean db = new DbBean();
//        List userlist = null;
//        try {
//            ResultSet rs = db.search("select * from identitunit");
//            userlist = new ArrayList();
//            while (rs.next()) {
//                userlist.add(rs.getString("name"));
//            }
//        } catch (SQLException ex) {
//            logger.error(ex);
//        } finally {
//            try {
//                db.result.close();
//                db.stat.close();
//                db.conn.close();
//            } catch (SQLException ex) {
//                logger.error(ex);
//            }
//        }
//        return userlist;
//    }
//    @SuppressWarnings("null")
//    public boolean alterUserInfo(List identit) {
//        DbBean db = new DbBean();
//        boolean b = false;
//        MD5Encoding md5 = new MD5Encoding();
////        PreparedStatement ps = null; where id=?
//        Statement stmt = null;
//        try {
//            stmt = db.conn.createStatement();
//            db.conn.setAutoCommit(false);
//            Iterator it = identit.iterator();
//            String sql = "";
////            ps = db.conn.prepareStatement("update identit set jobnumber=?,password=?,name=?,email=? where id=?");
//            //只能單筆，複數以上出錯
//            while (it.hasNext()) {
//                Identit i = (Identit) it.next();
//                sql = "update identit set name='" + i.getName() + "',email='" + i.getEmail() + "'";
////                jobnumber='" + i.getJobnumber() + "',
//                if (!i.getPassword().equals("")) {
//                    sql = sql + ",password='" + md5.getMD5Str(i.getJobnumber().trim() + i.getPassword().trim()) + "'";
//                    System.out.print(sql);
//                }
//                sql = sql + " where id=" + i.getId();
////                ps.setString(1, i.getJobnumber());
////                ps.setString(2, md5.getMD5Str(i.getJobnumber().trim() + i.getPassword().trim()));
////                ps.setString(3, i.getName());
////                ps.setString(4, i.getEmail());
////                ps.setInt(5, i.getId());
////                ps.addBatch();
//            }
//            stmt.executeUpdate(sql);
////            ps.executeBatch();
//            db.conn.commit();
//            b = true;
//        } catch (SQLException | NoSuchAlgorithmException | UnsupportedEncodingException ex) {
//            logger.error(ex);
//            try {
//                db.conn.rollback();
//            } catch (SQLException ex1) {
//                logger.error(ex1);
//            }
//        } finally {
//            try {
//                db.conn.setAutoCommit(true);
//                stmt.close();
//                db.conn.close();
//            } catch (SQLException ex) {
//                logger.error(ex);
//            }
//        }
//        return b;
//    }
//
//    @SuppressWarnings("null")
//    public boolean alterPassword(String jobnumber, String password, int id) {
//        DbBean db = new DbBean();
//        PreparedStatement ps = null;
//        boolean b = false;
//        MD5Encoding md5 = new MD5Encoding();
//        try {
//            password = md5.getMD5Str(jobnumber + password);
//            db.conn.setAutoCommit(false);
//            ps = db.conn.prepareStatement("update identit set password=? where id=?");
//            ps.setString(1, password);
//            ps.setInt(2, id);
//            ps.executeUpdate();
//            db.conn.commit();
//            b = true;
//        } catch (SQLException ex) {
//            logger.error(ex);
//            try {
//                db.conn.rollback();
//            } catch (SQLException ex1) {
//                logger.error(ex1);
//            }
//        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
//            logger.error(ex);
//        } finally {
//            try {
//                db.conn.setAutoCommit(true);
//                ps.close();
//                db.conn.close();
//            } catch (SQLException ex) {
//                logger.error(ex);
//            }
//        }
//        return b;
//    }
//    @SuppressWarnings("null")
//    public boolean deleteIdentit(int id) {
//        DbBean db = new DbBean();
//        PreparedStatement stmt = null;
//        boolean b = false;
//        try {
//            db.conn.setAutoCommit(false);
//            stmt = db.conn.prepareStatement("delete from identit where id=?");
//            stmt.setInt(1, id);
//            stmt.executeUpdate();
//            db.conn.commit();
//            b = true;
//        } catch (SQLException ex) {
//            logger.error(ex);
//            try {
//                db.conn.rollback();
//            } catch (SQLException ex1) {
//                logger.error(ex1);
//            }
//        } finally {
//            try {
//                db.conn.setAutoCommit(true);
//                stmt.close();
//                db.conn.close();
//            } catch (SQLException ex) {
//                logger.error(ex);
//            }
//        }
//        return b;
//    }
//
//    @SuppressWarnings("null")
//    public boolean new_user(List identit) {
//        DbBean db = new DbBean();
//        boolean b = false;
//        MD5Encoding md5 = new MD5Encoding();
//        PreparedStatement ps = null;
//        try {
//            db.conn.setAutoCommit(false);
//            Iterator it = identit.iterator();
//            ps = db.conn.prepareStatement("insert into identit (jobnumber,password,name,email) values(?,?,?,?)");
//            while (it.hasNext()) {
//                Identit i = (Identit) it.next();
//                ps.setString(1, i.getJobnumber());
//                ps.setString(2, md5.getMD5Str(i.getJobnumber().trim() + i.getPassword().trim()));
//                ps.setString(3, i.getName());
//                ps.setString(4, i.getEmail());
//                ps.addBatch();
//            }
//            ps.executeBatch();
//            db.conn.commit();
//            b = true;
//        } catch (SQLException | NoSuchAlgorithmException | UnsupportedEncodingException ex) {
//            logger.error(ex);
//            try {
//                db.conn.rollback();
//            } catch (SQLException ex1) {
//                logger.error(ex1);
//            }
//        } finally {
//            try {
//                db.conn.setAutoCommit(true);
//                ps.close();
//                db.conn.close();
//            } catch (SQLException ex) {
//                logger.error(ex);
//            }
//        }
//        return b;
//    }
}
