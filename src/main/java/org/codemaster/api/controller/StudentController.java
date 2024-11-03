package org.codemaster.api.controller;

import org.codemaster.api.model.Student;
import org.codemaster.api.service.StudentService;
import org.codemaster.litespringboot.annotations.Autowired;
import org.codemaster.litespringboot.annotations.RequestMapping;
import org.codemaster.litespringboot.annotations.RestController;

import java.util.Collection;

@RestController()
public class StudentController {

    @Autowired
    private final StudentService studentService;


    public StudentController() {
        this.studentService = new StudentService();
    }

    @RequestMapping(url = "/student/findAll")
    public Collection<Student> getAllStudentData(){
        return studentService.getALlStudent();
    }

}
