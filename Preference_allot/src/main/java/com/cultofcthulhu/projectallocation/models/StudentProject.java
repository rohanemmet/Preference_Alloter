package com.cultofcthulhu.projectallocation.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class StudentProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String studentName;
    private int studentNumber;
    private double studentGPA;
    private String projectName;

    public StudentProject() {}
    public StudentProject(String studentName, int studentNumber, double studentGPA, String projectName) {
        this.studentName = studentName;
        this.studentNumber = studentNumber;
        this.studentGPA = studentGPA;
        this.projectName = projectName;
    }

    public int getId() {
        return id;
    }

    public String getStudentName() {
        return studentName;
    }

    public int getStudentNumber() {
        return studentNumber;
    }

    public double getStudentGPA() {
        return studentGPA;
    }

    public String getProjectName() {
        return projectName;
    }
}
