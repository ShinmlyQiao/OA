package com.xingxing.oa.aspect;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;

/**
 * 日志切面
 */

@Slf4j
@Configuration
@Aspect
public class LogAspect {

    private static final String CONTROLLER_POINT_CUT="execution(* com.xingxing.oa.*.controller.*.*(..))";

    @Autowired
    private ObjectMapper mapper;

    @Pointcut(value = CONTROLLER_POINT_CUT)
    public void controllerPointCut(){}


    @Around(value = "controllerPointCut()")
    public Object controllerAround(ProceedingJoinPoint joinPoint) throws Throwable{
        try {
            HttpServletRequest request = (HttpServletRequest) RequestContextHolder.getRequestAttributes();
            log.info("ip地址：{}",request.getRemoteAddr());
            log.info("请求url:{}",request.getRequestURL());
            log.info("请求方法：{}", joinPoint.getSignature().toString());

            Object[] args = joinPoint.getArgs();
            if (args.length > 0) {
                for (int i = 0; i < args.length; i++) {
                    log.info("参数{}：{}", i, mapper.writeValueAsString(args));
                }
            }

        }catch (Exception e){
            throw new RuntimeException("参数日志切面报错",e);
        }

        Object proceed = joinPoint.proceed();

        return proceed;
    }

}
