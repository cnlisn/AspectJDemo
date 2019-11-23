package com.lisn.aspectjdemo;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/**
 * Author: LiShan
 * Time: 2019-11-23
 * Description: 网络状态检查
 */

/**
 * AnnotationRetention.SOURCE：不存储在编译后的 Class 文件。
 * AnnotationRetention.BINARY：存储在编译后的 Class 文件，但是反射不可见。
 * AnnotationRetention.RUNTIME：存储在编译后的 Class 文件，反射可见。
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckNet {
}