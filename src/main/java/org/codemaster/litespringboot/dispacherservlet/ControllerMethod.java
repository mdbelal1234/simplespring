package org.codemaster.litespringboot.dispacherservlet;

import lombok.Builder;
import lombok.Data;
import org.codemaster.litespringboot.enums.MethodType;

import java.lang.reflect.Method;

@Builder
@Data
public class ControllerMethod {

    private Class<?> clz;
    private Method method;
    private String mappedUrl;
    private MethodType methodType;
    private Object instance;
}
