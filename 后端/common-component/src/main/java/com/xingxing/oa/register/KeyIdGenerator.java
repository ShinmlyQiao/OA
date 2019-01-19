package com.xingxing.oa.register;

import com.xingxing.oa.annotions.GeneratorId;
import com.xingxing.oa.constrants.EnviromentConstrants;
import com.xingxing.oa.reflect.ScanClassUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Configuration
@ConditionalOnProperty(value =EnviromentConstrants.SCAN_PACKAGE )
@Slf4j
public class KeyIdGenerator {

    @Value("${"+ EnviromentConstrants.SCAN_PACKAGE+"}")
    private String packagePath;

    @Autowired
    private RedisTemplate redisTemplate;

    @EventListener(ApplicationStartedEvent.class)
    public void registerDomainId(){
        log.info("程序已经启动，扫描 domain id start");
        Objects.requireNonNull(packagePath);
        Set<Class<?>> packageClass = ScanClassUtil.getPackageClass(packagePath);
        List<String> keyList = packageClass.stream().map(clazz -> {
            GeneratorId annotation = clazz.getAnnotation(GeneratorId.class);
            List<String> idKeyList = new ArrayList<>();
            if (Objects.nonNull(annotation)) {
                String value = annotation.value();
                idKeyList.add(value);
            }
            //只获取自己类的，继承的父类有此注解，不算
            List<Field> fieldList = Arrays.asList(clazz.getDeclaredFields());
            List<String> fieldIdKeyList = fieldList.stream().filter(field -> field.isAnnotationPresent(GeneratorId.class))
                    .map(field -> field.getAnnotation(GeneratorId.class)).map(GeneratorId::value).collect(Collectors.toList());
            if (Objects.nonNull(fieldIdKeyList) && !fieldIdKeyList.isEmpty()) {
                idKeyList.addAll(fieldIdKeyList);
            }
            return idKeyList;
        }).flatMap(flat -> flat.stream()).collect(Collectors.toList());

        if(Objects.nonNull(keyList)) {
            log.info("扫描到key:{}", keyList);
            HashOperations hashOperations = redisTemplate.opsForHash();
            keyList.stream().forEach(key -> {
                Boolean hasPut = hashOperations.putIfAbsent("oaid", key, 1000);
                if (hasPut) {
                    log.info("{}不存在，已经新增", key);
                } else {
                    log.info("{}已存在", key);
                }
            });
        }
        log.info("扫描 domain id finish");
    }


}
