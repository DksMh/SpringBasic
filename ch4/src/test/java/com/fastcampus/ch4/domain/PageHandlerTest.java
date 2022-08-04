//package com.fastcampus.ch4.domain;
//
//import org.junit.Test;
//
//import static org.junit.Assert.*;
//
//public class PageHandlerTest {
//    @Test
//    public void test(){
//        PageHandler ph = new PageHandler(250, 1); // 총 게시물 : 250 현재 페이지 : 1
//        ph.print();
//        System.out.println("ph = " + ph);
//        assertTrue(ph.getBeginPage() == 1);
//        assertTrue(ph.getEndPage() == 10);
//    }
//    @Test
//    public void test2(){
//        PageHandler ph = new PageHandler(250, 11);
//        ph.print();
//        System.out.println("ph = " + ph);
//        assertTrue(ph.getBeginPage() == 11);
//        assertTrue(ph.getEndPage() == 20);
//    }
//
//    @Test
//    public void test3(){
//        PageHandler ph = new PageHandler(255, 25); // 전체 페이지 26
//        ph.print();
//        System.out.println("ph = " + ph);
//        assertTrue(ph.getBeginPage() == 21);
//        assertTrue(ph.getEndPage() == 26);
//    }
//
//    @Test
//    public void test4(){
//        PageHandler ph = new PageHandler(255, 10);
//        ph.print();
//        System.out.println("ph = " + ph);
//        assertTrue(ph.getBeginPage() == 1);
//        assertTrue(ph.getEndPage() == 10);
//    }
//}