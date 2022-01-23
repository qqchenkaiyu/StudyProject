package com.study.JUC;


import lombok.Data;

import java.util.concurrent.locks.ReentrantLock;
@Data
public class MyLock {
    private MyAQS myAQS = new MyAQS();

    public void lock() {
        myAQS.lock();
    }

    public void unlock() {
        myAQS.unlock();
    }

    public MyCondition newCondition() {
        ReentrantLock lock = new ReentrantLock();
        MyCondition myCondition = new MyCondition();
        myCondition.setMyLock(this);
        return myCondition;
    }


}
