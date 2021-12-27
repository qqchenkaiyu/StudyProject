package com.study.JUC;

import lombok.Data;

import java.util.LinkedList;
import java.util.concurrent.locks.LockSupport;

@Data
public class MyCondition {
    LinkedList<Thread> conditionList = new LinkedList();

    MyLock myLock;

    public void await() {
        // 当前线程已经拿到锁 需要释放锁 进入conditionList
        myLock.unlock();

        conditionList.add(Thread.currentThread());

        LockSupport.park();

        myLock.lock();

    }

    public void signalAll() {
        myLock.aqsList.addAll(conditionList);
        conditionList.clear();

    }
}
