package com.butchjgo.dao;

import com.butchjgo.entity.Student;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;

public class StudentDAO implements DAO<Student> {
    private List<Student> students = new LinkedList<>();

    public StudentDAO() {
    }

    @PostConstruct
    void prepareTempData() {
        students.add(new Student("SE62100", "NGUYEN DAM DUC THOAI", "SE1073", 21));
        students.add(new Student("SE62111", "NGUYEN VAN A", "SE1067", 24));
        students.add(new Student("SE1181", "TRAN THI B", "PC1098", 18));
    }

    @Override
    public void create(Student student) {
        students.add(student);
    }

    @Override
    public void updateById(String id) {

    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public Student searchById(String id) {
        return null;
    }

    @Override
    public List<Student> getAll() {
        return this.students;
    }
}
