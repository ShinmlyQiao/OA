package com.xingxing.oa.string;

import java.util.Objects;

public class StringUtils {

    public static  boolean isBlank(String str){
        if(Objects.isNull(str)||str.isBlank()){
            return true;
        }
        return false;
    }

    public static  void isBlankMessage(String str){
        isBlankMessage(str,"字符串不允许为空");
    }
    public static  void isBlankMessage(String str,String message){
        if(Objects.isNull(str)||str.isBlank()){
            throw new RuntimeException(message);
        }
    }

    public static boolean isNotBlank(String str){
        return !isBlank(str);
    }
}
