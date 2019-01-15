package com.xingxing.oa.clazz;

import org.junit.Test;

import java.util.Set;

public class TestScanClass {

    @Test
    public void test01(){
        Set<Class<?>> strings = ScanClass.getPackageClass("com");
        strings.forEach(System.out::println);
    }
}
