package com.yejunjie.lambda;

import java.io.File;
import java.io.FileFilter;

/**
 * 方法引用说明
 */
public class MethodRef {

    public static void main(String[] args) {

        //未使用方法引用
        File[] hiddenFiles = new File("F:\\test").listFiles(new FileFilter() {
            public boolean accept(File file) {
                return !file.isHidden();
            }
        });

        // 函数式接口
        // 1
        File[] hiddenFile1 = new File("F:\\test").listFiles((File file)->!file.isHidden());

        //2 省略参数类型
        File[] hiddenFile2 = new File("F:\\test").listFiles(file->!file.isHidden());

        //方法引用 最简洁
        File[] hiddenFile = new File("F:\\test").listFiles(File::isHidden);
    }
}
