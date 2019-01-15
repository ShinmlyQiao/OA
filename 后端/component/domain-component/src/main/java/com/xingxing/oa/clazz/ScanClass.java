package com.xingxing.oa.clazz;


import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.stream.Collectors;

@Slf4j
public class ScanClass {
    public static Set<Class<?>> getPackageClass(String packagePathStr){
        Set<String> strPackageClass = ScanClass.scanPackageClass(packagePathStr);
        return strPackageClass.stream().map(c-> {
            try {
               return Thread.currentThread().getContextClassLoader().loadClass(c);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toSet());
    }

    private static Set<String> scanPackageClass(String packagePathStr) {
        Set<String> clazzStrSet = Collections.emptySet();
        log.info("开始扫描包:{}",packagePathStr);
        if(Objects.isNull(packagePathStr)||packagePathStr.isBlank()){
           return clazzStrSet;
        }

        String packageFilePathStr = packagePathStr.replaceAll("\\.","/");
        Enumeration<URL> resources = null;
        try {
            resources = Thread.currentThread().getContextClassLoader().getResources(packageFilePathStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(Objects.isNull(resources)){
            return clazzStrSet;
        }
        Set<String> clazzSet = new HashSet<>();
        while(resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            String protocol = resource.getProtocol();
            if ("file".equals(protocol)) {
                log.info("file协议");
                try {
                    String filePath = URLDecoder.decode(resource.getFile(), "UTF-8");
                    clazzStrSet = readClassFromFile(filePath, packageFilePathStr, clazzSet);
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            } else if ("jar".equals(protocol)) {
                log.info("jar协议");
                String fileUrl = resource.getFile();
                int pos = fileUrl.indexOf('!');
                if (-1 == pos) {
                    return Collections.emptySet();
                }
                clazzStrSet = readClassFromJar(fileUrl.substring(5, pos), packagePathStr,clazzSet);
            }
        }
        return  clazzStrSet;
    }

    private static Set<String> readClassFromFile(String packageFilePathStr, String packagePathStr, Set<String> clazzSet) {
        File dir = new File(packageFilePathStr);
        if(!dir.exists()){
            return Collections.EMPTY_SET;
        }
        Deque<File> deque = new ArrayDeque<>(50);

        File[] files = dir.listFiles(file -> file.isDirectory() || file.getName().endsWith(".class"));
        if(Objects.nonNull(files)&&files.length>0){
            deque.addAll(Arrays.asList(files));
        }
        while(!deque.isEmpty()){
            File file = deque.pollLast();
            Optional.ofNullable(file).ifPresent(f->{
                if(f.isDirectory()){
                    File[] fs = f.listFiles(fi -> fi.isDirectory() || fi.getName().endsWith(".class"));
                    if(Objects.nonNull(fs)&&fs.length>0){
                        deque.addAll(Arrays.asList(fs));
                    }
                }else {
                    int first = f.getAbsolutePath().indexOf(packagePathStr);
                    int last = f.getAbsolutePath().lastIndexOf(packagePathStr);
                    if(first!=last){
                        log.warn("包名重复：{}",packagePathStr);
                    }
                    clazzSet.add(getFullClassName(f.getAbsolutePath().substring(first)));
                }
            });
        }
        return clazzSet;

    }

    private static Set<String> readClassFromJar(String packageFilePathStr, String packagePathStr, Set<String> clazzSet){
        JarInputStream jarInputStream;
        try {
            jarInputStream = new JarInputStream(new FileInputStream(packageFilePathStr));
            JarEntry jarEntry = jarInputStream.getNextJarEntry();
            while (Objects.nonNull(jarEntry)){
                if(jarEntry.isDirectory()){
                    jarEntry = jarInputStream.getNextJarEntry();
                    continue;
                }
                String entryName = jarEntry.getName();
                if(entryName.endsWith(".class")&&entryName.startsWith(packagePathStr)){
                    clazzSet.add(getFullClassName(entryName));
                }
                jarEntry = jarInputStream.getNextJarEntry();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return clazzSet;
    }

    private static String getFullClassName(String path) {
        return path.replaceAll("[/\\\\]","\\.").substring(0,path.length()-6);
    }
}
