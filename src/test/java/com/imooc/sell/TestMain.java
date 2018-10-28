package com.imooc.sell;

public class TestMain {


    public static void main(String[] args){
        try {
            TestMain testMain = new TestMain();
            testMain.tryTest();

            System.out.println("=================");
//            throw new Exception("Nothing");
            //throw new Error("nothing");// 切换这两行试试??
        } catch (Exception e) {
            System.out.println("捕获到异常。。");
            e.printStackTrace(System.err);
        }finally{
            System.out.println("finally 语句。。");
        }
        //
        System.out.println("try 外部的后面的代码。。。");
    }



    public void tryTest() throws Exception {
        try {
//            throw new Exception("12321312321");
//            System.out.println("tryTest1");
            int c = 1/0;
        }catch (Exception e){
//            System.out.println("tryTest2");
//            throw new Exception("12321312321");
            return;
        }
        System.out.println("tryTest3");
//        int c = 1/0;
//        System.out.println("99999999999999");
    }



}
