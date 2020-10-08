package com.cultofcthulhu.projectallocation.modeltests;

import com.cultofcthulhu.projectallocation.models.StudentProject;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentProjectTests {

    @Test
    public void testConstructor() {
        StudentProject studentProject = new StudentProject("Jack Price", 17329086, 4.2, "Project Allocation");

        assertEquals("Jack Price", studentProject.getStudentName());
        assertEquals(17329086, studentProject.getStudentNumber());
        assertEquals(4.2, studentProject.getStudentGPA());
        assertEquals("Project Allocation", studentProject.getProjectName());
    }
}
