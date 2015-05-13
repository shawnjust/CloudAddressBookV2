package com.shawn.tongji.cloudaddressbook.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by shawn on 5/13/15.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Display {
    String fieldKey();
    int drawableId() default -1;
    int gravity() default Integer.MAX_VALUE;
}
