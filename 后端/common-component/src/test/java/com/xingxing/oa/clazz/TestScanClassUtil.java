package com.xingxing.oa.clazz;

import com.xingxing.oa.reflect.ScanClassUtil;
import org.junit.Test;

import java.util.Set;

public class TestScanClassUtil {

    @Test
    public void test01(){
        Set<Class<?>> strings = ScanClassUtil.getPackageClass("com.xingxing.oa");
        strings.forEach(System.out::println);
    }
}
