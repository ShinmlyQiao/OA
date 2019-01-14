package com.xingxing.oa.clazz;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class ScanClass {

    public Set<Class> scanPackageClass(String packagePathStr) {
        if(Objects.isNull(packagePathStr)||packagePathStr.isBlank()){
           return Collections.emptySet();
        }
        String packageFilePathStr = packagePathStr.replaceAll(".","/");
        URL resource = this.getClass().getClassLoader().getResource(packageFilePathStr);
        String protocol = resource.getProtocol();
        if("file".equals(protocol)){
            try {
                String filePath = URLDecoder.decode(resource.getFile(), "UTF-8");


            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }else if("jar".equals(protocol)){
            Set<String> clazzStr = readJarFromJar(packageFilePathStr,packagePathStr);

        }


        return  null;
    }

    private Set<String> readJarFromJar(String packageFilePathStr, String packagePathStr){
        JarInputStream jarInputStream;
        Set<String> clazzSet = new HashSet<>();
        try {
            jarInputStream = new JarInputStream(new FileInputStream(packageFilePathStr));
            JarEntry jarEntry = jarInputStream.getNextJarEntry();
            while (Objects.nonNull(jarEntry)){
                if(jarEntry.isDirectory()){
                    continue;
                }
                String entryName = jarEntry.getName();
                if(entryName.endsWith(".class")&&entryName.startsWith(packagePathStr)){
                    clazzSet.add(entryName.substring(0,entryName.length()-6));
                }
                jarEntry = jarInputStream.getNextJarEntry();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return clazzSet;
    }
}
