package org.codemaster.litespringboot.dispacherservlet;


import org.codemaster.litespringboot.annotations.RequestMapping;
import org.codemaster.litespringboot.annotations.RestController;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RestControllerMethodMap {

    private final Map<Class<?>, Object> components;
    private Map<String,ControllerMethod> controllerMethodMap;

    private static RestControllerMethodMap instance;

    private RestControllerMethodMap(Map<Class<?>, Object> components) {
        this.components = components;
        controllerMethodMap = new HashMap<>();
        initializeControllerMethods();
    }

    public static RestControllerMethodMap getInstance(Map<Class<?>, Object> components){
        Objects.requireNonNull(components);
        if (instance == null) {
            instance = new RestControllerMethodMap(components);
        }
        return instance;
    }

    private void initializeControllerMethods() {
        for (Object component : components.values()) {
            if (isRestController(component)) {
                mapControllerMethods(component);
            }
        }
    }

    private boolean isRestController(Object component) {
        return component.getClass().isAnnotationPresent(RestController.class);
    }

    private void mapControllerMethods(Object component) {
        for (Method method : component.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(RequestMapping.class)) {
                ControllerMethod controllerMethod = createControllerMethod(component, method);
                if (controllerMethod != null) {
                    controllerMethodMap.put(controllerMethod.getMappedUrl(), controllerMethod);
                }
            }
        }
    }

    private ControllerMethod createControllerMethod(Object component, Method method) {
        try {
            RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
            return ControllerMethod.builder()
                    .mappedUrl(requestMapping.url())
                    .methodType(requestMapping.type())
                    .clz(method.getDeclaringClass())
                    .instance(component)
                    .build();
        } catch (Exception e) {
            System.err.println("Error mapping method: " + method.getName() + " - " + e.getMessage());
            return null;
        }
    }

    public Map<String, ControllerMethod> getControllerMethodMap() {
        return controllerMethodMap;
    }
}
