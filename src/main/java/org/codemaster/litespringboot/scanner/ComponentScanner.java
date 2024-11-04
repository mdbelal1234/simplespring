package org.codemaster.litespringboot.scanner;

import org.codemaster.litespringboot.annotations.Autowired;
import org.codemaster.litespringboot.annotations.Component;
import org.codemaster.litespringboot.annotations.RestController;
import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ComponentScanner {

    private final Map<Class<?>, Object> components = new HashMap<>();
    private static ComponentScanner instance;

    private ComponentScanner() {
    }

    // Singleton instance getter
    public static synchronized ComponentScanner getInstance() {
        if (instance == null) {
            instance = new ComponentScanner();
        }
        return instance;
    }

    public void scan(String packageName){
        try {
            scanComponents(packageName);
            injectDependencyToComponent();
        } catch (Exception e) {
            System.out.println("ComponentScanner: Can't scan the result properly!");
            throw new RuntimeException(e);
        }
    }


    private void scanComponents(String packageName) throws Exception {
        String path = packageName.replace('.', '/');
        File directory = new File(Objects.requireNonNull(getClass().getClassLoader().getResource(path)).getFile());

        if (!directory.exists()) {
            throw new IllegalArgumentException("Package " + packageName + " not found.");
        }

        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.isFile() && file.getName().endsWith(".class")) {
                String className = packageName + "." + file.getName().replace(".class", "");
                Class<?> clazz = Class.forName(className);

                if (isComponent(clazz)) {
                    Object instance = clazz.getDeclaredConstructor().newInstance();
                    components.put(clazz, instance);
                }
            }
        }
    }

    private void injectDependencyToComponent() throws IllegalAccessException {
        for (Object component : components.values()) {
            for (Field field : component.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    Class<?> fieldType = field.getType();
                    Object dependency = components.get(fieldType);

                    if (dependency != null) {
                        field.setAccessible(true);
                        field.set(component, dependency);
                    }
                }
            }
        }

    }


    private boolean isComponent(Class<?> clazz){
        return clazz.isAnnotationPresent(Component.class)
                || clazz.isAnnotationPresent(RestController.class);
    }

    public <T> T getComponent(Class<T> clazz) {
        return clazz.cast(components.get(clazz));

    }
}
