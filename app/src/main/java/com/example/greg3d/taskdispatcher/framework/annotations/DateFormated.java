package com.example.greg3d.taskdispatcher.framework.annotations;

import com.example.greg3d.taskdispatcher.constants.DateFormats;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by greg3d on 05.11.17.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface DateFormated {
    String value() default DateFormats.DATE_TIME_FORMAT;
}
