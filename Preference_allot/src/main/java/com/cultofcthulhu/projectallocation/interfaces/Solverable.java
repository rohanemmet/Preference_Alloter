package com.cultofcthulhu.projectallocation.interfaces;

import com.cultofcthulhu.projectallocation.models.Solution;
import com.cultofcthulhu.projectallocation.models.data.ProjectDAO;
import com.cultofcthulhu.projectallocation.models.data.StudentDAO;

import java.util.Map;

public interface Solverable {
    public Solution hillClimb(double GPA_impact, StudentDAO studentDAO, ProjectDAO projectDAO);
    public double assessSolution(Solution solution, double GPA_impact, StudentDAO studentDAO, ProjectDAO projectDAO);


}
