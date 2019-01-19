package com.xingxing.oa.collection;

import java.util.Collection;
import java.util.Objects;

public class CollectionUtils {

    public static  <E> boolean isEmpty(Collection<E> collection){
        if(Objects.isNull(collection)||collection.isEmpty()){
            return true;
        }
        return false;
    }

    public static <E> void isEmptyMessage(Collection<E> collection){
        if(Objects.isNull(collection)||collection.isEmpty()){
            throw new RuntimeException("集合不允许为空");
        }
    }

    public static <E> void isEmptyMessage(Collection<E> collection,String message){
        if(Objects.isNull(collection)||collection.isEmpty()){
            throw new RuntimeException(message);
        }
    }

}
