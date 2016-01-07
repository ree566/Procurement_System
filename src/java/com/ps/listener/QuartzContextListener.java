///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.ps.listener;
//
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.servlet.ServletContextEvent;
//import javax.servlet.ServletContextListener;
//import org.quartz.Scheduler;
//import org.quartz.SchedulerException;
//import org.quartz.impl.StdSchedulerFactory;
//
///**
// *
// * @author Wei.Cheng
// */
//public class QuartzContextListener implements ServletContextListener {
//
//    Scheduler scheduler;
//
//    @Override
//    public void contextInitialized(ServletContextEvent sce) {
//        try {
//            scheduler = StdSchedulerFactory.getDefaultScheduler();
//            scheduler.start();
////        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        } catch (SchedulerException ex) {
//            Logger.getLogger(QuartzContextListener.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        //web初始化當下呼叫排程(每3日刪除一次資料庫資料)
//    }
//
//    @Override
//    public void contextDestroyed(ServletContextEvent sce) {
////        WebApplicationContext webApplicationContext = (WebApplicationContext) sce
////                .getServletContext()
////                .getAttribute(
////                        WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
////        org.quartz.impl.StdScheduler startQuertz = (org.quartz.impl.StdScheduler) webApplicationContext
////                .getBean("startQuertz");
////        if (startQuertz != null) {
////            startQuertz.shutdown();
////        }
////        try {
////            Thread.sleep(1000);
////        } catch (InterruptedException e) {
////            e.printStackTrace();
////        }
//
//        try {
//            if (scheduler != null) {
//                scheduler.shutdown();
//            }
//            Thread.sleep(1000);
//        } catch (SchedulerException | InterruptedException ex) {
//            Logger.getLogger(QuartzContextListener.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        //安裝quartz後當tomcat重新啟動時會出現memory leak(http://www.cnblogs.com/leeying/p/3782102.html)
//        //使用以上方法
//    }
//
//}
