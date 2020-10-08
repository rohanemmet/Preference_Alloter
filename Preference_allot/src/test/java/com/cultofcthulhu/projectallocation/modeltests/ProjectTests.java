package com.cultofcthulhu.projectallocation.modeltests;

import com.cultofcthulhu.projectallocation.models.Project;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProjectTests {

    @Test
    public void testConstructor() {
        Project project = new Project(0, "App for destruction", 0);

        assertEquals(0, project.getId());
        assertEquals("App for destruction", project.getProjectTitle());
        assertEquals(0, project.getProposedBy());
    }

    @Test
    public void testToString() {
        Project project = new Project(0, "App for destruction", 0);

        assertEquals("0,App for destruction", project.toString());
    }
}
