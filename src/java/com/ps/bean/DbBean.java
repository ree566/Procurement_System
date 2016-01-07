/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ps.bean;

import java.io.Serializable;
import java.sql.*;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 *
 * @author Wei.Cheng
 */
public class DbBean implements Serializable {
    /*
     change the database driver because of
     http://www.javaworld.com.tw/jute/post/view?bid=6&id=131471&tpg=1&ppg=1&sty=1&age=0
     http://www.javaworld.com.tw/jute/post/view?bid=21&id=366&sty=1&tpg=1&age=-1
        
     old: "jdbc:sqlserver://NB991001\\KEVIN;databaseName=Internal_Check";
     the different is jtds no need to provide the instance name
     */

    private DataSource dataSource;

    Connection conn = null;
    Statement stat = null;
    ResultSet result = null;
    private DbBean db = null;
    PreparedStatement preparedstatement = null;
    private static Logger logger = null;

    public DbBean() {
        BasicConfigurator.configure();
        logger = Logger.getLogger(DbBean.class);
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/demo");
            conn = dataSource.getConnection();
        } catch (NamingException | SQLException ex) {
            logger.error(ex);
        }
    }

    public ResultSet search(String sql) throws SQLException {
        stat = conn.createStatement();
        result = stat.executeQuery(sql);
        return result;
    }

    public ResultSet search(String sql, int row) throws SQLException {
//        preparedstatement = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stat = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        result = stat.executeQuery(sql);  
        result.absolute(row);
        return result;
    }
}
