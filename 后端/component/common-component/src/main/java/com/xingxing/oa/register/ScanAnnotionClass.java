package com.xingxing.oa.register;

import com.xingxing.oa.annotion.GeneratorId;
import com.xingxing.oa.constrants.EnviromentConstrants;
import com.xingxing.oa.reflect.ScanClassUtil;
import com.xingxing.oa.reflect.ScanFieldUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@ConditionalOnProperty(EnviromentConstrants.SCAN_PACKAGE)
public class ScanAnnotionClass {

    @Value(EnviromentConstrants.SCAN_PACKAGE)
    private String packagePath;


    @EventListener(ApplicationReadyEvent.class)
    public void registrDomainId(){
        Set<Class<?>> packageClass = ScanClassUtil.getPackageClass(packagePath);
        List<String> idKey = packageClass.stream().map(clazz -> {
            GeneratorId annotation = clazz.getAnnotation(GeneratorId.class);
            List<String> idKeyList = new ArrayList<>();
            if (Objects.nonNull(annotation)) {
                String value = annotation.value();
                idKeyList.add(value);
            }
            List<Field> fieldList = ScanFieldUtil.getClassField(clazz);
            List<String> fieldIdKeyList = fieldList.stream().filter(field -> field.isAnnotationPresent(GeneratorId.class))
                    .map(field -> field.getAnnotation(GeneratorId.class)).map(GeneratorId::value).collect(Collectors.toList());
            if (Objects.nonNull(fieldIdKeyList) && !fieldIdKeyList.isEmpty()) {
                idKeyList.addAll(fieldIdKeyList);
            }
            return idKeyList;
        }).flatMap(flat -> flat.stream()).collect(Collectors.toList());

    }

}
