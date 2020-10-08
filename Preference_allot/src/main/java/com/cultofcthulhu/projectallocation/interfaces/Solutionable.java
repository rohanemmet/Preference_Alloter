package com.cultofcthulhu.projectallocation.interfaces;

import com.cultofcthulhu.projectallocation.models.Solution;
import com.cultofcthulhu.projectallocation.models.data.ProjectDAO;
import com.cultofcthulhu.projectallocation.models.data.StudentDAO;

import java.util.List;

public interface Solutionable {
    public Solution generateSolution(StudentDAO studentDAO, ProjectDAO projectDAO);
}
