package com.study.JUC;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.LinkedList;
import java.util.concurrent.locks.LockSupport;

@Slf4j
public class MyAQS {
    // 0释放  >0 占用
    private volatile int state;
    private Unsafe unsafe = getUnsafe();
    // 内部维护一个链表 node1->node2
    LinkedList<Thread> aqsList = new LinkedList();

    public void enq() {
        System.out.println(Thread.currentThread() + "入队");
        aqsList.add(Thread.currentThread());
    }

    @SneakyThrows
    public void lock() {
        long stateOffset = unsafe.objectFieldOffset(MyAQS.class.getDeclaredField("state"));
        while (!unsafe.compareAndSwapInt(this, stateOffset, 0, 1)) {
            enq();
            LockSupport.park();
            // unsafe.park(false,0l);
        }
        log.info("成功得到锁 " + state);
    }

    // 释放占用的公共资源
    public void unlock() {
        log.info("准备释放锁 现在state是" + state);
        state--;
        if (state == 0) {
            log.info("释放锁");
            if (!aqsList.isEmpty()) {
                Thread first = aqsList.removeFirst();
                log.info("唤醒线程--" + first);
                LockSupport.unpark(first);
            }
        }
    }

    private Unsafe getUnsafe() {
        Field fld = (Field) AccessController.doPrivileged(
                new PrivilegedAction() {
                    public Object run() {
                        Field fld = null;

                        try {
                            Class unsafeClass = sun.misc.Unsafe.class;
                            fld = unsafeClass.getDeclaredField("theUnsafe");
                            fld.setAccessible(true);
                            return fld;
                        } catch (NoSuchFieldException exc) {
                            Error err = new Error("Could not access Unsafe");
                            err.initCause(exc);
                            throw err;
                        }
                    }
                }
        );

        Unsafe unsafe = null;

        try {
            unsafe = (Unsafe) (fld.get(null));
        } catch (Throwable t) {
            Error err = new Error("Could not access Unsafe");
            err.initCause(t);
            throw err;
        }

        return unsafe;
    }
}
