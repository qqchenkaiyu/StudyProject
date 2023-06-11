package com.study;

import cn.hutool.core.date.StopWatch;
import cn.hutool.core.util.RuntimeUtil;

public class TestUtil {
    private static StopWatch stopWatch = new StopWatch("打印方法执行耗时");
    static {
        RuntimeUtil.addShutdownHook(()-> System.out.println(stopWatch.prettyPrint()));
    }
    public static void executeMethod(Runnable runnable,
                                     String name){
        stopWatch.start(name);
        runnable.run();
        stopWatch.stop();
    }
}
