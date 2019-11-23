package com.lisn.aspectjdemo;


import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Calendar;

/**
 * @author : lishan
 * @e-mail : cnlishan@163.com
 * @date : 2019-11-23 15:03
 * @desc :
 */

@Aspect
public class SingleClickAspect {
    private String TAG = "SingleClickAspect";
    private int MIN_CLICK_DELAY_TIME = 600;
    private long lastClickTime = 0L;

    @Pointcut("execution(@com.lisn.aspectjdemo.SingleClick * *(..))")//方法切入点
    public void methodAnnotated() {

    }

    /**
     * joinPoint.proceed() 执行注解所标识的代码
     *
     * @After 可以在方法前插入代码
     * @Before 可以在方法后插入代码
     * @Around 可以在方法前后各插入代码
     */
    @Around("methodAnnotated()")
    public Object aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {//过滤掉600毫秒内的连续点击
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "currentTime:" + currentTime);
            }
            lastClickTime = currentTime;
            //执行原方法
            return joinPoint.proceed();
        }
        return null;
    }

}