package com.cultofcthulhu.projectallocation.models;

import com.cultofcthulhu.projectallocation.models.data.ProjectDAO;
import com.cultofcthulhu.projectallocation.models.data.StudentDAO;
import com.cultofcthulhu.projectallocation.models.data.StudentProjectDAO;
import com.cultofcthulhu.projectallocation.system.systemVariables;

import javax.persistence.*;
import java.util.*;

public class Solution implements Comparable<Solution>{

    private Map<Integer, Integer> solution;
    private Integer[] studentProjectAssignmentOrder;
    private double energy;
    private double fitness;

    public Solution(Map<Integer, Integer> map, Integer[] studentProjectAssignmentOrder)
    {
        this.studentProjectAssignmentOrder = studentProjectAssignmentOrder.clone();
        solution = new HashMap<>(map);
    }

    public Solution(Map<Integer, Integer> map, double energy) {
        solution = new HashMap<>(map);
        this.energy = energy;
        fitness = -energy;
    }

    public Solution(Integer[] studentProjectAssignmentOrder)
    {
        this.studentProjectAssignmentOrder = studentProjectAssignmentOrder;
    }

    public Solution(StudentDAO studentDAO, ProjectDAO projectDAO) {
        generateSolution(studentDAO, projectDAO);
    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public void setStudentProjectAssignmentOrder(Integer[] studentProjectAssignmentOrder) {
        this.studentProjectAssignmentOrder = studentProjectAssignmentOrder;
    }

    public Integer[] getStudentProjectAssignmentOrder() {
        return studentProjectAssignmentOrder;
    }

    public Map<Integer, Integer> getSolution() {
        return solution;
    }

    public void generateSolution(StudentDAO studentDao, ProjectDAO projectDAO) {
        Integer[] studentAssignmentOrder;
        List<Student> students = studentDao.findAll();
        List<Project> projects = projectDAO.findAll();

        if(studentProjectAssignmentOrder == null) {
            studentAssignmentOrder = new Integer[students.size()];

            for(int i = 0; i < studentAssignmentOrder.length; i++) studentAssignmentOrder[i] = i + 1;
            Collections.shuffle(Arrays.asList(studentAssignmentOrder));
            this.studentProjectAssignmentOrder = studentAssignmentOrder;
        }
        else studentAssignmentOrder = studentProjectAssignmentOrder;
        Map<Integer, Integer> solution = new HashMap<>();
        Boolean[] takenProjects = new Boolean[projects.size()];
        Arrays.fill(takenProjects, false);


        for(Integer integer : studentAssignmentOrder) {
            Student student = students.get(integer-1);
            Map<Integer, Integer> preferences = student.getPreferences();
            for(int x = 0; x < preferences.size(); x++) {
                if(!takenProjects[preferences.get(x) - 1]) {
                    takenProjects[preferences.get(x) - 1] = true;
                    solution.put(student.getId(), preferences.get(x));
                    break;
                }
            }
            if(solution.get(student.getId()) == null) {
                for(int i = 0; i < takenProjects.length; i++) {
                    if(!takenProjects[i] && projects.get(i).getProposedBy() == 0) {
                        takenProjects[i] = true;
                        solution.put(student.getId(), i + 1);
                        break;
                    }
                }
            }
        }
        this.solution = solution;
    }

    public void change() {
        Random rand = new Random();

        int x = rand.nextInt(studentProjectAssignmentOrder.length);
        int y = rand.nextInt(studentProjectAssignmentOrder.length);
        Collections.swap(Arrays.asList(studentProjectAssignmentOrder),x,y);
    }

    public String printSolution(StudentDAO studentDAO, ProjectDAO projectDAO){

        String out = "";

        for(Map.Entry<Integer, Integer> entry : solution.entrySet()) {
            out = out + studentDAO.getOne(entry.getKey()).getName() + " " + studentDAO.getOne(entry.getKey()).getGpa();
            int num = -1;
            for (Map.Entry<Integer,  Integer> preference: studentDAO.getOne(entry.getKey()).getPreferences().entrySet())
            {
                if (preference.getValue().equals(entry.getValue())){
                    num = preference.getKey() + 1;
                }
            }
            out = out + "\t\t(" + num + ")\t\t" + projectDAO.getOne(entry.getValue()).getProjectTitle() + "\n";
        }
        return out;
    }

    public List<String> solutionQualityReport(StudentDAO studentDAO){
        List<String> out = new ArrayList<>();
        int[] count = new int[systemVariables.NUMBER_OF_PREFERENCES + 1];
        Arrays.fill(count, 0);

        for(Map.Entry<Integer, Integer> entry : solution.entrySet()) {
            int num = 0;
            for (Map.Entry<Integer,  Integer> preference: studentDAO.getOne(entry.getKey()).getPreferences().entrySet())
            {
                if (preference.getValue().equals(entry.getValue())){
                    num = preference.getKey() + 1;
                }
            }

            count[num]++;
        }

        for (int i = 1 ; i < count.length ; i++){
            out.add(count[i] + " student(s) got their " + ordinal(i) + " preference\n");
        }

        out.add(count[0] + " student(s) didn't get a preference on their list");

        boolean everyStudentHasUniqueProject = true;
        boolean everyStudentHasAPreference = true;
        for(Map.Entry<Integer, Integer> entry : getSolution().entrySet()) {
            //First check if a project has been assigned to more than one person
            for (Map.Entry<Integer, Integer> entry2 : getSolution().entrySet())
                if (entry.getValue().equals(entry2.getValue()) && !entry.getKey().equals(entry2.getKey())) {
                    out.add("CONSTRAINT VIOLATION : PROJECT ASSIGNED TO MORE THEN ONE STUDENT");
                    everyStudentHasUniqueProject = false;
                }
                if(entry.getValue() == -1) {
                    out.add("CONSTRAINT VIOLATION : STUDENT NOT ASSIGNED PROJECT ON THEIR LIST");
                    everyStudentHasAPreference = false;
                }
        }

        if (everyStudentHasUniqueProject) {
            out.add("Ever student has a unique project");
        }

        if (everyStudentHasAPreference) {
            out.add("Ever student has a project from their preferences");
        }

        return out;
    }

    private String ordinal(int i) {
        i = i % 100;
        String[] ordinals = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
        if (i == 11 || i == 12 || i == 13) {
            return i + "th";
        } else {
            return i + ordinals[i % 10];
        }
    }

    public List<String[]> getSolutionList(StudentDAO studentDAO, ProjectDAO projectDAO, StudentProjectDAO studentProjectDAO){

        List<String[]> stringList = new ArrayList<>();

        for(Map.Entry<Integer, Integer> entry : solution.entrySet()) {
            String[] out = new String[5];
            out[0] = studentDAO.getOne(entry.getKey()).getName();
            out[1] = String.valueOf(studentDAO.getOne(entry.getKey()).getGpa());
            int num = -1;
            for (Map.Entry<Integer, Integer> preference: studentDAO.getOne(entry.getKey()).getPreferences().entrySet())
            {
                if (preference.getValue().equals(entry.getValue())){
                    num = preference.getKey() + 1;
                }
            }
            out[2] = String.valueOf(num);
            out[3] = projectDAO.getOne(entry.getValue()).getProjectTitle();
            if(num <= 5) out[4] = "rgba(76, 175, 80, 0.2)";
            if(num > 5 && num <=10) out[4] = "rgb(255,255,0, 0.2)";
            if(num > 10 && num <= 15) out[4] = "rgb(255,165,0, 0.2)";
            if(num > 15 && num <= 20) out[4] = "rgb(255,0,0, 0.2)";
            if(num == -1) out[4] = "rgb(0,0,0, 0.2)";
            stringList.add(out);
        }

        List<StudentProject> selfProposed = studentProjectDAO.findAll();
        for (StudentProject curr : selfProposed) {
            String[] out = new String[5];
            out[0] = curr.getStudentName();
            out[1] = String.valueOf(curr.getStudentGPA());
            out[2] = "1";
            out[3] = curr.getProjectName();
            out[4] = "rgba(76, 175, 80, 0.1)";

            stringList.add(out);
        }
        return stringList;
    }

    @Override
    public int compareTo(Solution o) {
        return Double.compare(o.getFitness(), fitness);
    }
}
