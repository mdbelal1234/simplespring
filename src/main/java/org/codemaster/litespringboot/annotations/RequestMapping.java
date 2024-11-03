package org.codemaster.litespringboot.annotations;

import org.codemaster.litespringboot.enums.MethodType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
public @interface RequestMapping {
    String url() default "";
    MethodType type() default MethodType.GET;
}
