package com.cultofcthulhu.projectallocation.models;

import com.cultofcthulhu.projectallocation.interfaces.Personable;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Student implements Personable {

    //Variable declaration and constructors
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int studentID;
    private String name;
    private double gpa = 0.00;
    @ElementCollection
    private Map<Integer, Integer> preferences = new HashMap<>();
    private int assignedProjectID = -1;
    private int workingUnderStaffID;

    public Student() {}
    public Student(String name){
        this.name = name;
    }
    public Student(String name, double GPA) {
        this.name = name;
        this.gpa = GPA;
    }
    public Student(String name, int studentID, double GPA) {
        this.name = name;
        this.studentID = studentID;
        this.gpa = GPA;
    }

    //Getters and setters
    public int getId() {
        return id;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getGpa() { return gpa; }

    public void setGpa(double gpa) { this.gpa = gpa; }

    public void addPreference(int i){
        preferences.put(preferences.size(), i);
    }

    public void setAssignedProjectID(int i){
        assignedProjectID = i;
    }

    public int getAssignedProjectID() {
        return assignedProjectID;
    }

    public void setWorkingUnderStaffID(int i){
        workingUnderStaffID = i;
    }

    public int getWorkingUnderStaffID() {
        return workingUnderStaffID;
    }

    public Map<Integer, Integer> getPreferences() {
        return preferences;
    }

    //Extra methods
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(int preference : preferences.values())
            sb.append(preference).append(",");
        return name + "," + id + ",\"" + sb + "\"," + gpa;
    }
}
