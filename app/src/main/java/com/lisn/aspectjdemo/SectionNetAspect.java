
package com.lisn.aspectjdemo;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Author: LiShan
 * Time: 2019-11-23
 * Description: 处理网络监测切面
 *
 * Advice（通知）: 典型的 Advice 类型有 before、after 和 around，分别表示在目标方法执行之前、执行后和完全替代目标方法执行的代码。
 *
 * Joint point（连接点）: 程序中可能作为代码注入目标的特定的点和入口。
 *
 * Pointcut（切入点）: 告诉代码注入工具，在何处注入一段特定代码的表达式。
 *
 * Aspect（切面）: Pointcut 和 Advice 的组合看做切面。例如，在本例中通过定义一个 pointcut 和给定恰当的advice，添加一个了内存缓存的切面。
 *
 * Weaving（织入）: 注入代码（advices）到目标位置（joint points）的过程。
 *
 */
@Aspect
public class SectionNetAspect {

    private static final String TAG = "SectionNetAspect";

    /**
     * 找到处理的切点
     * * *(..)  可以处理所有的方法
     */
    @Pointcut("execution(@com.lisn.aspectjdemo.CheckNet * *(..))")
    public void checkNetBehavior() {

    }

    /**
     * 处理切面
     */
    @Around("checkNetBehavior()")
    public Object checkNet(ProceedingJoinPoint joinPoint) throws Throwable {
        Log.e(TAG, "checkNet");
        //做埋点  日志上传  权限监测

        //网络监测
        //1. 获取CheckNet注解
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        CheckNet checkNet = signature.getMethod().getAnnotation(CheckNet.class);
        if (checkNet != null) {
            //2. 判断有没有网络
            Log.e(TAG, "判断有没有网络");
            Object object = joinPoint.getThis();
            Context context = getContext(object);
            if (!isNetworkAvailable(context)) {
                // 3.没有网络不要往下执行
                Toast.makeText(context, "请检查您的网络", Toast.LENGTH_SHORT).show();
                return null;
            }
        }

        //继续下一个注解那里去执行
        return joinPoint.proceed();
    }

    private Context getContext(Object object) {
        if (object instanceof Activity) {
            return (Activity) object;
        } else if (object instanceof Fragment) {
            return ((Fragment) object).getActivity();
        } else if (object instanceof View) {
            return ((View) object).getContext();
        }
        return null;
    }

    /**
     * 检查当前网络是否可用
     *
     * @return
     */
    private static boolean isNetworkAvailable(Context context) {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}

