///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.ps.test;
//
//import com.ps.entity.Accounting;
//import com.ps.entity.Product;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.xssf.usermodel.XSSFCell;
//import org.apache.poi.xssf.usermodel.XSSFRow;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.quartz.Job;
//import static org.quartz.JobBuilder.newJob;
//import org.quartz.JobDetail;
//import org.quartz.JobExecutionContext;
//import org.quartz.JobExecutionException;
//import org.quartz.Scheduler;
//import org.quartz.SchedulerException;
//import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
//import org.quartz.Trigger;
//import static org.quartz.TriggerBuilder.newTrigger;
//import org.quartz.impl.StdSchedulerFactory;
//
///**
// *
// * @author Wei.Cheng
// */
//public class test {
//
//    public static void main(String[] s) {
//
//        
//        try {  
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
////            Date begindate = sdf.parse("2015/8/5 20:15:15");
//            Date thistime = sdf.parse(sdf.format(new Date()));
////            long day=(thistime.getTime()-begindate.getTime())/(24*60*60*1000);  
//            System.out.print(sdf.format(new Date()));
//        } catch (ParseException ex) {
//            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//
//// define the job and tie it to our HelloJob class
////    JobDetail job = newJob(test.class)
////        .withIdentity("job1", "group1")
////        .build();
////
////    // Trigger the job to run now, and then repeat every 40 seconds
////    Trigger trigger = newTrigger()
////        .withIdentity("trigger1", "group1")
////        .startNow()
////        .withSchedule(simpleSchedule()
////                .withIntervalInSeconds(40)
////                .repeatForever())            
////        .build();
////
////    // Tell quartz to schedule the job using our trigger
////    scheduler.scheduleJob(job, trigger);
//
//
//    }
//
//    
//    @SuppressWarnings("ConvertToTryWithResources")
//    public static Map getData() throws IOException {
//        String st = "";
//        int price = 0;
//        List l = new ArrayList();
//        Map map = new HashMap();
//        FileInputStream fis = new FileInputStream(new File("C:\\Users\\Wei.Cheng\\Desktop\\project.xlsx"));
//        XSSFWorkbook workbook = new XSSFWorkbook(fis);
//        XSSFSheet sheet = workbook.getSheetAt(0);
//        Iterator ite = sheet.rowIterator();
//        int type = -1;
//        while (ite.hasNext()) {
//            Row row = (Row) ite.next();
////            System.out.println(row.getRowNum());
//            Product p = new Product();
//            Cell cell = row.getCell(0);
//            if (cell != null) {
//                st = cell.toString();
//                switch (st.trim()) {
//                    case "包材":
//                        type = 1;
//                        break;
//                    case "間材":
//                        type = 2;
//                        break;
//                    case "雜項":
//                        type = 3;
//                        break;
//                    case "雜項-工具":
//                        type = 4;
//                        break;
//                }
//                p.setName(st);
//            }
//            Cell cell2 = row.getCell(1);
//            if (cell2 != null && !"".equals(cell2.toString()) && !cell2.toString().equals("金額")) {
//                float f = Float.parseFloat(cell2.toString());
//                p.setPrice((int)f);
//            }
//            map.put(p, type);
//        }
//        fis.close();
//        return map;
//    }
//
//}
