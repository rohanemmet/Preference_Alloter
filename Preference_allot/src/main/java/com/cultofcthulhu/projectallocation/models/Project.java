package com.cultofcthulhu.projectallocation.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Project {

    //Variable declaration and constructors
    @Id
    private int id;

    private String projectTitle;
    private int proposedBy = 0;
    private int studentAssigned;

    public Project() {}

    public Project(int id, String projectTitle, int proposedBy) {
        this.id = id;
        this.projectTitle = projectTitle;
        this.proposedBy = proposedBy;
    }

    //Getters and setters
    public int getId() {
        return id;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public int getProposedBy() {
        return proposedBy;
    }

    public void setProposedBy(int proposedBy) {
        this.proposedBy = proposedBy;
    }

    public void setStudentAssigned(int studentAssigned) {
        this.studentAssigned = studentAssigned;
    }

    public int getStudentAssigned() {
        return studentAssigned;
    }

    //Extra methods
    @Override
    public String toString() {
        return proposedBy + "," + projectTitle;
    }
}
