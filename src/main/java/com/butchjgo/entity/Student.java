package com.butchjgo.entity;

import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
public class Student {

    //@NotNull
    private String code;
    @NotNull
    private String name;
    @NotNull
    private String classCode;
    @Min(1)
    private int age;

    public Student() {
    }

    public Student(String code, String name, String classCode, int age) {
        this.code = code;
        this.name = name;
        this.classCode = classCode;
        this.age = age;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
