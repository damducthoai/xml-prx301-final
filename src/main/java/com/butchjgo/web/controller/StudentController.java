package com.butchjgo.web.controller;

import com.butchjgo.dao.DAO;
import com.butchjgo.entity.Student;
import com.butchjgo.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "students")
public class StudentController {

    @Autowired
    DAO<Student> studentDAO;

    @Autowired
    IdGenerator idGenerator;

    @GetMapping
    ResponseEntity getStudents() {
        return  ResponseEntity.ok(studentDAO.getAll());
    }

    @PostMapping
    ResponseEntity createStudent(@Valid @RequestBody Student student, BindingResult result) {
        ResponseEntity res = ResponseEntity.badRequest().body("data not valid");
        if (!result.hasErrors()) {
            student.setCode(idGenerator.generate());
            studentDAO.create(student);
            res = ResponseEntity.ok(student);
        }
        return res;
    }

    @DeleteMapping("{studentId}")
    ResponseEntity deleteStudent(@PathVariable("studentId") String studentId) {
        studentDAO.deleteById(studentId);
        return ResponseEntity.ok().body("delete successfully");
    }

}
