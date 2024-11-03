package org.codemaster.api.repository;

import org.codemaster.litespringboot.annotations.Component;
import org.codemaster.api.model.Student;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class StudentRepo {
    private static final Map<Integer, Student> STUDENT_TABLE = new HashMap<>();

    static {
        initializeStudents();
    }

    private static void initializeStudents() {
        STUDENT_TABLE.put(1, new Student(1, "John", "Doe", "johndoe@gmail.com"));
        STUDENT_TABLE.put(2, new Student(2, "Porcupine", "Tree", "porcupinetree@gmail.com"));
        STUDENT_TABLE.put(3, new Student(3, "Hello", "World", "helloworld@gmail.com"));
        STUDENT_TABLE.put(4, new Student(4, "Tom", "Cat", "tomcat@gmail.com"));
        STUDENT_TABLE.put(5, new Student(5, "Spring", "Boot", "springboot@gmail.com"));
    }

    public boolean addStudent(Student student) {
        if (!STUDENT_TABLE.containsKey(student.getId())) {
            STUDENT_TABLE.put(student.getId(), student);
            return true;
        } else {
            System.out.println("This ID already exists in the db!");
            return false;
        }
    }

    public Student getStudentById(int id) {
        return STUDENT_TABLE.get(id);
    }

    public Collection<Student> getAllStudents() {
        return Collections.unmodifiableCollection(STUDENT_TABLE.values());
    }
}