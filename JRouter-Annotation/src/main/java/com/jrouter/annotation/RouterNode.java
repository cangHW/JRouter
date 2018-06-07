package com.jrouter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/4 14:27.
 * Function :路由节点
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface RouterNode {
    /**
     * path of the router node
     */
    String path();

    /**
     * group of the router node
     */
    String group() default "other";

    /**
     * desc of the router node
     */
    String desc() default "";
}
