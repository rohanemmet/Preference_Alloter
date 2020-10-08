package com.cultofcthulhu.projectallocation.models;

import java.util.ArrayList;
import java.util.List;

public class GeneticAlgorithmSolutionHerd {
    private List<Solution> solutionList = new ArrayList<>();

    public GeneticAlgorithmSolutionHerd(Solution initialSolution) {
        solutionList.add(initialSolution);
    }

    public Solution getSolution(int index) {
        return solutionList.get(index);
    }

    public void addSolution(Solution solution) {
        solutionList.add(solution);
    }

    public int size() {
        return solutionList.size();
    }

    public void cullSolutions(int percent) {
        int number = (int) Math.round((solutionList.size()/100.0) * percent);
        number = solutionList.size() - number;
        solutionList.subList(solutionList.size()-number, solutionList.size()).clear();
    }

    public void sortSolutions() {
        solutionList.sort(Solution::compareTo);
    }

}
