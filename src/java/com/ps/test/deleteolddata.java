///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.ps.test;
//
//import com.ps.bean.OrderContentBean;
//import com.ps.bean.OrderListBean;
//import com.ps.entity.OrderContent;
//import com.ps.entity.OrderList;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Set;
//import java.util.TreeSet;
//import org.apache.log4j.BasicConfigurator;
//import org.apache.log4j.Logger;
//import org.quartz.Job;
//import org.quartz.JobExecutionContext;
//import org.quartz.JobExecutionException;
//
///**
// *
// * @author Wei.Cheng
// */
//public class deleteolddata implements Job {
//
//    private static Logger logger = null;
////    private Thread thread;
//
//    @Override
//    public void execute(JobExecutionContext jec) throws JobExecutionException {
//        BasicConfigurator.configure();
//        logger = Logger.getLogger(deleteolddata.class);
////        thread = Thread.currentThread();
//        List list = new ArrayList();
//        OrderListBean olBean = new OrderListBean();
//        OrderContentBean ocBean = new OrderContentBean();
//        Set set = new TreeSet();
//        boolean b = false;
//        OrderContent oc = new OrderContent();
//        List l = olBean.getOrderList();
//        Iterator it = l.iterator();
//        while (it.hasNext()) {
//            OrderList ol = (OrderList) it.next();
//            long date = countDate(ol.getO_date());
//            if (date >= 14 && ol.getStatus() > 0) {
//                set.add(ol.getId());
//                //改成list批次修改?
//                //每隔3天把狀態為0以上且過期14天的訂單全部砍掉(1已完成2失敗)
//            }
//        }
//        b = olBean.deleteOrderList(set);
//        logger.info("delete orderlist  : " + b);
//    }
//
//    public long countDate(String date) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//        Date begindate = null;
//        try {
//            begindate = sdf.parse(date);
//            Date thistime = sdf.parse(sdf.format(new Date()));
//            long day = (thistime.getTime() - begindate.getTime()) / (24 * 60 * 60 * 1000);
//            return day;
//        } catch (ParseException ex) {
//            return -99999;
//        }
//    }
//}
