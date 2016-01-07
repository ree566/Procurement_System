/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ps.test;

import com.ps.entity.OrderTotal;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wei.Cheng
 */
public class testjava {

    public static void main(String st[]) throws IOException {
//        OrderTotal ot = new OrderTotal();
//        ot.setListid(1);
//        ot.setApplicant(10);
//        OrderTotal ot2 = new OrderTotal();
//        ot2.setListid(1);
//        ot2.setApplicant(5);
//        System.out.print(ot.equals(ot2));
//        System.out.print(ot == ot2);
//        
//        Set<OrderTotal> set = new HashSet<OrderTotal>();
//        set.add(ot);
//        set.add(ot2);
//        System.out.print(set.size());

        String str = "正es則abb表c示213";
        str = str.replace(",", "");
        System.out.print(">> " + str.toUpperCase() + " <<");
//        String reg = /^[\u4e00-\u9fa5]+$/;
    }
}
