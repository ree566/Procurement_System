/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ps.bean;

import com.ps.entity.Product;
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
public class ProductBean implements Serializable {

    private DbBean db = null;
    private PreparedStatement preparedstatement = null;
    private static Logger logger = null;

    public ProductBean() {
        BasicConfigurator.configure();
        logger = Logger.getLogger(ProductBean.class);
    }

    public List getProduct() {
        db = new DbBean();
        List product = null;
        try {
            ResultSet rs = db.search("select * from product");
            product = new ArrayList();
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setPrice(rs.getInt("price"));
                p.setMoq(rs.getInt("moq"));
                p.setUnit(rs.getInt("unit"));
                p.setType(rs.getInt("type"));
                p.setComp(rs.getInt("comp"));
//                if(rs.getString("status").equals("true")){
                p.setStatus(rs.getInt("status"));
//                }
                product.add(p);
            }
        } catch (Exception ex) {
            logger.error(ex);
        } finally {
            try {
                db.result.close();
//                db.preparedstatement.close();
                db.stat.close();
                db.conn.close();
            } catch (SQLException ex) {
                logger.error(ex);
            }
        }
        return product;
    }

    public boolean updateProductStatus(List p) {
        String sql = "update product set status=? where id=?";
        boolean b = alterProduct(p, sql, 3);
        return b;
    }

    //新增product
    public boolean insertProduct(List p) {
        String sql = "insert into product(name,price,moq,type,unit,discount,status,comp) values (?,?,?,?,?,?,?,?)";
        boolean b = alterProduct(p, sql, 1);
        return b;
    }

    public boolean updateProduct(List p) {
        String sql = "update product set name=?,price=?,moq=?,type=?,unit=?,comp=? where id=?";
        boolean b = alterProduct(p, sql, 2);
        return b;
    }

    public boolean deleteProduct(List p) {
        String sql = "delete product where id=?";
        boolean b = alterProduct(p, sql, 0);
        return b;
    }

    public boolean alterProduct(List product, String sql, int type) {
        db = new DbBean();
        boolean b = false;
        try {
            db.conn.setAutoCommit(false);
            Iterator it = product.iterator();
            preparedstatement = db.conn.prepareStatement(sql);
            while (it.hasNext()) {
                Product p = (Product) it.next();
                switch (type) {
                    case 0:
                        preparedstatement.setInt(1, p.getId());
                        break;
                    case 1:
                        preparedstatement.setString(1, p.getName());
                        preparedstatement.setInt(2, p.getPrice());
                        preparedstatement.setInt(3, p.getMoq());
                        preparedstatement.setInt(4, p.getType());
                        preparedstatement.setInt(5, p.getUnit());
                        preparedstatement.setFloat(6, 1);
                        preparedstatement.setInt(7, 1);
                        preparedstatement.setInt(8, p.getComp());
                        break;
                    case 2:
                        preparedstatement.setString(1, p.getName());
                        preparedstatement.setInt(2, p.getPrice());
                        preparedstatement.setInt(3, p.getMoq());
                        preparedstatement.setInt(4, p.getType());
                        preparedstatement.setInt(5, p.getUnit());
                        preparedstatement.setFloat(6, p.getComp());
                        preparedstatement.setInt(7, p.getId());
                        break;
                    case 3:
                        preparedstatement.setInt(1, p.getStatus());
                        preparedstatement.setInt(2, p.getId());
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
