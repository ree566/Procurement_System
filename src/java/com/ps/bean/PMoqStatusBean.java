/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ps.bean;

import com.ps.entity.PMoqStatus;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 *
 * @author Wei.Cheng
 */
public class PMoqStatusBean implements Serializable {

    private DbBean db = null;
    private static Logger logger = null;

    public PMoqStatusBean() {
        BasicConfigurator.configure();
        logger = Logger.getLogger(PMoqStatusBean.class);
    }

    public List getPMoqStatus() {
        db = new DbBean();
        List pmslist = null;
        try {
            ResultSet rs = db.search("select * from p_moq_status");
            pmslist = new ArrayList();
            while (rs.next()) {
                PMoqStatus pms = new PMoqStatus();
                pms.setPid(rs.getInt("pid"));
                pms.setProduct(rs.getString("product"));
                pms.setQuantity_total(rs.getInt("quantity_total"));
                pms.setP_moq(rs.getInt("p_moq"));
                pms.setDiff(rs.getInt("diff"));
                pmslist.add(pms);
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
        return pmslist;
    }
}
