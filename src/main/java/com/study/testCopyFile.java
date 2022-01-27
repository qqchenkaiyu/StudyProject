/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2022-2022. All rights reserved.
 */

package com.study.copyFileTest;

import cn.hutool.core.io.FileUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class test {
    public static void main(String[] args) throws Exception {

        File s = new File("D:\\struts2帮助文档.rar");
        // 如果源文件不存在就创建源文件
        if (!s.exists()) {
            s.createNewFile();
            FileUtil.writeBytes(new byte[1024 * 1024 * 1000], s);
        }

        File t = new File("D:\\struts2帮助文档2.rar");
        long start, end;

        try (FileChannel src = new FileInputStream(s).getChannel();
            FileChannel dest = new FileOutputStream(t).getChannel();) {
            ByteBuffer byteBuffer;

            byteBuffer = ByteBuffer.allocate(1024 * 1024 * 1000);
            start = System.currentTimeMillis();
            src.read(byteBuffer);
            byteBuffer.flip();
            dest.write(byteBuffer);
            end = System.currentTimeMillis();
            System.out.println("分配jvm内存进行拷贝");
            System.out.println(end - start);
        } finally {
            t.delete();
        }

        try (FileChannel src = new FileInputStream(s).getChannel();
            FileChannel dest = new FileOutputStream(t).getChannel();) {
            ByteBuffer byteBuffer;

            byteBuffer = ByteBuffer.allocateDirect(1024 * 1024 * 1000);
            start = System.currentTimeMillis();
            src.read(byteBuffer);
            byteBuffer.flip();

            dest.write(byteBuffer);
            end = System.currentTimeMillis();
            System.out.println("分配本地内存进行拷贝");
            System.out.println(end - start);
        } finally {
            t.delete();
        }

        try (FileChannel src = new FileInputStream(s).getChannel();
            FileChannel dest = new FileOutputStream(t).getChannel();) {
            start = System.currentTimeMillis();
            src.transferTo(0, src.size(), dest);//连接两个通道，并且从in通道读取，然后写入out通道
            end = System.currentTimeMillis();
            System.out.println("transferTo进行拷贝");
            System.out.println(end - start);
        } finally {
            t.delete();
        }

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(s));
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(t));) {
            start = System.currentTimeMillis();
            byte[] b = new byte[1024 * 1024];
            while (bis.read(b) != -1) {
                bos.write(b);
            }
            end = System.currentTimeMillis();
            System.out.println("java bio流 进行拷贝");
            System.out.println(end - start);
        } finally {
            t.delete();
        }

        start = System.currentTimeMillis();
        Files.copy(s.toPath(), t.toPath(), new CopyOption[] {
            StandardCopyOption.REPLACE_EXISTING
        });
        end = System.currentTimeMillis();

        System.out.println("调用 Files.copy 进行拷贝");
        System.out.println(end - start);
        t.delete();
    }
}
