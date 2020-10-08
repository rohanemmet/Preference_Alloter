package com.cultofcthulhu.projectallocation.models;

import com.cultofcthulhu.projectallocation.interfaces.Personable;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class StaffMember implements Personable {

    //Variable declaration and constructors
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int staffID;
    private String name;
    private String researchInterests;
    @ElementCollection
    private Map<Integer, Integer> project_proposals = new HashMap<>();

    public StaffMember() {}

    public StaffMember(int id, String name, String researchInterests) {
        this.staffID = id;
        this.name = name;
        this.researchInterests = researchInterests;;
    }

    //Getters and setters
    public int getId() {
        return id;
    }

    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getResearchInterests() {
        return researchInterests;
    }

    public void setResearchInterests(String interests) {
        this.researchInterests = interests;
    }

    public void setProject_proposals(Map<Integer, Integer> project_proposals) {
        this.project_proposals = project_proposals;
    }

    public void addProject_proposal(int projectProposal){
       project_proposals.put(project_proposals.size(), projectProposal);
    }


    //Extra methods
    @Override
    public String toString() {
        return id + "," + name + "," + researchInterests;
    }
}
