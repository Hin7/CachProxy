package ru.sbt.course.cacheproxy;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cache {
    static int FILE = 0;
    static int IN_MEMORY =1;
    int cachType() default IN_MEMORY;
    String fileName() default "data.cch";
    boolean zip() default false;
    Class[] identityBy() default {};
    int listLength() default 0;
}
