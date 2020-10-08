package com.cultofcthulhu.projectallocation.solvers;

import com.cultofcthulhu.projectallocation.models.GeneticAlgorithmSolutionHerd;
import com.cultofcthulhu.projectallocation.models.Solution;
import com.cultofcthulhu.projectallocation.models.Student;
import com.cultofcthulhu.projectallocation.models.data.ProjectDAO;
import com.cultofcthulhu.projectallocation.models.data.StudentDAO;
import com.cultofcthulhu.projectallocation.solutionAccess;
import com.cultofcthulhu.projectallocation.system.systemVariables;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class GeneticAlgorithm {
    private GeneticAlgorithmSolutionHerd solutions;
    private int progress;
    private final int MAX_POPULATION_SIZE = systemVariables.MAX_POPULATION_SIZE;
    private final int MATE_PERCENTAGE = systemVariables.MATE_PERCENTAGE;
    private final int CULL_PERCENTAGE = systemVariables.CULL_PERCENTAGE;
    private final double MUTATION_CHANCE = systemVariables.MUTATION_CHANCE;

    public GeneticAlgorithm(GeneticAlgorithmSolutionHerd initialSolutions) {
        solutions = initialSolutions;
    }

    public Solution runAlgorithm(double GPA_impact, StudentDAO studentDAO, ProjectDAO projectDAO) {
        int generationLimit = 0;
        double bestFitness;
        //Generate a bunch of initial solutions
        for(int i = 0; i < MAX_POPULATION_SIZE; i++) {
            Solution solution = new Solution(studentDAO, projectDAO);
            solution.setFitness(assessSolution(solution, GPA_impact, studentDAO, projectDAO));
            solutions.addSolution(solution);
        }
        solutions.sortSolutions();
        //Main loop:
        do {
            bestFitness = solutions.getSolution(0).getFitness();
            //Allow top % to mate until we reach max population size
            while (solutions.size() < MAX_POPULATION_SIZE) {
                int solution1, solution2;
                do {
                    solution1 = ThreadLocalRandom.current().nextInt(0, (int) ((MAX_POPULATION_SIZE / 100.0) * MATE_PERCENTAGE));
                    solution2 = ThreadLocalRandom.current().nextInt(0, (int) ((MAX_POPULATION_SIZE / 100.0) * MATE_PERCENTAGE));
                } while (solution1 == solution2);
                System.out.println("Mating solutions " + solution1 + " and " + solution2);
                Solution offspringSolution = mate(solutions.getSolution(solution1), solutions.getSolution(solution2));
                offspringSolution = mutate(offspringSolution);
                offspringSolution.generateSolution(studentDAO, projectDAO);
                offspringSolution.setFitness(assessSolution(offspringSolution, GPA_impact, studentDAO, projectDAO));
                System.out.println("New solution has fitness " + offspringSolution.getFitness());
                solutions.addSolution(offspringSolution);
            }
            //Order list according to fitness
            solutions.sortSolutions();
            //Cull bottom %
            solutions.cullSolutions(CULL_PERCENTAGE);
            //Repeat until a plateau is reached i.e. No significant fitness improvements
            if(solutions.getSolution(0).getFitness() <= bestFitness) {
                generationLimit++;
            } else generationLimit = 0;
        } while(generationLimit < 5);
        System.out.println(solutions.getSolution(0).printSolution(studentDAO, projectDAO));
        new solutionAccess().solutionSaveToFile(solutions.getSolution(0), "solution", solutions.getSolution(0).printSolution(studentDAO, projectDAO));
        return solutions.getSolution(0);
    }

    public Solution mate(Solution solution1, Solution solution2) {

        Integer[] currentSolutionOrder;
        Integer[] new_solution_order = new Integer[systemVariables.NUMBER_OF_STUDENTS];
        Arrays.fill(new_solution_order, 0);

        int place = -1;

        //Loop through students in order of first solution student assignment order
        for (int studentNo = 0 ; studentNo < systemVariables.NUMBER_OF_STUDENTS ; studentNo++){
            int student = solution1.getStudentProjectAssignmentOrder()[studentNo]; //Set student to look at
            if(student % 2 == 1) currentSolutionOrder = solution1.getStudentProjectAssignmentOrder();
            else currentSolutionOrder = solution2.getStudentProjectAssignmentOrder();

            //Find space where current student was in old solution
            for (int x = 0 ; x < systemVariables.NUMBER_OF_STUDENTS ; x++) {
                if (currentSolutionOrder[x] == student) {
                    place = x;
                    //System.out.println("Student " + currentSolutionOrder[place] + " is in place : " + place);
                    break;
                }
            }
            boolean placed = false;
            do {
                //check if place in new solution is free to fill
                if (new_solution_order[place] == 0) {
                    new_solution_order[place] = student;
                    placed = true;
                } else {
                    //If space is full find next available space
                    place++;
                    //If next available space would be outside bounds of array, upshift all previous elements upto next free space
                    if (place == new_solution_order.length){
                        //System.out.println("[" + place + "] :" + Arrays.toString(new_solution_order));
                        for(int i = 0; i + 1 < new_solution_order.length; i++) {
                            if(new_solution_order[i] == 0) {
                                new_solution_order[i] = new_solution_order[i + 1];
                                new_solution_order[i + 1] = 0;
                            }
                        }
                        //System.out.println("[" + place + "] :" + Arrays.toString(new_solution_order));
                        place = new_solution_order.length - 1;
                    }
                }
            } while (!placed);
        }

        System.out.println("Solution 1st" +  Arrays.toString(solution1.getStudentProjectAssignmentOrder()));
        System.out.println("+ Solution 2nd" + Arrays.toString(solution2.getStudentProjectAssignmentOrder()) );
        System.out.println("= New Solution  : " + Arrays.toString(new_solution_order));

        return new Solution(solution1.getSolution(), new_solution_order);
    }

    public Solution mutate(Solution solution) {
        if(ThreadLocalRandom.current().nextInt(0, 100) <= MUTATION_CHANCE) {
            solution.change();
            System.out.println("Mutation!");
        }
        return solution;
    }

    public double assessSolution(Solution solution, double GPA_impact, StudentDAO studentDAO, ProjectDAO projectDAO) {
        double fitness = 0;

        if (violatesHardConstraints(solution.getSolution())) {return 0;}

        for(Map.Entry<Integer,Integer> currentPair : solution.getSolution().entrySet()) {
            Student currentStudent = studentDAO.getOne(currentPair.getKey());
            //First, add fitness based on how far down in each student's preference list their assigned project is
            //For each project that they weren't assigned, find the student that was assigned to it, and decrease fitness based on whether their GPA is lower than our current student's
            for(Map.Entry<Integer,Integer> preference : currentStudent.getPreferences().entrySet()) {
                if(preference.getValue().equals(currentPair.getValue())) fitness += systemVariables.NUMBER_OF_PREFERENCES - preference.getKey();
                else {
                    int studentID = projectToStudent(preference.getValue(), solution.getSolution());
                    if(studentID != -1) {
                        Student assignedStudent = studentDAO.getOne(studentID);
                        if(assignedStudent.getGpa() < currentStudent.getGpa())
                            fitness -= ((systemVariables.NUMBER_OF_STUDENTS / 6.0) * GPA_impact);
                    }
                }
            }
            if(!currentStudent.getPreferences().containsValue(currentPair.getValue())) fitness -= 50;
        }
        return fitness;
    }

    public boolean violatesHardConstraints(Map<Integer, Integer> map) {
        for(Map.Entry<Integer, Integer> entry : map.entrySet()) {
            //First check if a project has been assigned to more than one person
            for (Map.Entry<Integer, Integer> entry2 : map.entrySet())
                if (entry.getValue().equals(entry2.getValue()) && !entry.getKey().equals(entry2.getKey())) {
                    System.out.println("Project assigned to more than one student");
                    return true;
                }
            //Next check if a student has actually been assigned a project
            if(entry.getValue() == -1) {
                System.out.println("Student not assigned a project");
                return true;
            }
        }
        return false;
    }

    public int projectToStudent(int id, Map<Integer,Integer> solution) {
        for(Map.Entry<Integer, Integer> entry : solution.entrySet()) {
            if(entry.getValue() == id)
                return entry.getKey();
        }
        return -1;
    }

    public int getProgress() {
        return progress;
    }

    public Solution getBest() {
        return solutions.getSolution(0);
    }
}