package com.jrouter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/7 16:12.
 * Function :
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface ParamsNode {

    String key() default "";

    boolean canEmpty() default false;

    String desc() default "";

}
