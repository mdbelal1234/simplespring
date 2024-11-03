package org.codemaster.api.service;

import lombok.Data;
import org.codemaster.litespringboot.annotations.Autowired;
import org.codemaster.api.model.Student;
import org.codemaster.api.repository.StudentRepo;
import java.util.Collection;

@Data
public class StudentService {

    @Autowired
    private final StudentRepo studentRepo;

    public StudentService() {
        this.studentRepo = new StudentRepo();
    }

    public boolean addStudent(Student student){
        return studentRepo.addStudent(student);
    }

    public Collection<Student> getALlStudent(){
        return studentRepo.getAllStudents();
    }

}
