package com.study;


import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class Starter {
    public static void main(String[] args) {
//        testSpring();
//     testLock();
//        testCondition();

    }

 //   private static void testCondition() {
//
//        MyLock myLock = new MyLock();
//        MyCondition myCondition = myLock.newCondition();
//        Thread waitThread = new Thread(() -> {
//            myLock.lock();
//            log.info("我要等一个新信号");
//            myCondition.await();
//            log.info("拿到信号");
//            myLock.unlock();
//        }, "waitThread");
//        waitThread.start();
//        Thread signalThread = new Thread(() -> {
//            myLock.lock();
//            log.info("我拿到锁了");
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            myCondition.signalAll();
//            log.info("拿到信号");
//            myLock.unlock();
//        }, "signalThread");
//        signalThread.start();
//    }
//
//    private static void testLock() {
//        MyLock myLock = new MyLock();
//        ExecutorService executorService = Executors.newFixedThreadPool(6);
//        for (int i = 0; i < 7; i++) {
//            executorService.submit(() -> {
//                myLock.lock();
//                System.out.println("我开始运行啦~~·  ----" + Thread.currentThread());
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                myLock.unlock();
//            });
//        }
//        executorService.shutdown();
//    }

    private static void testSpring() {
        //        ApplicaionContext context=new ApplicaionContext("beans.setting");
//        PersonInterface person=context.getBean(Person.class);
//        person.hello();
    }
}
