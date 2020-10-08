package com.cultofcthulhu.projectallocation.modeltests;

import com.cultofcthulhu.projectallocation.models.Student;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentTests {

    @Test
    public void testConstructor() {
        Student student = new Student("Jack Price", 4.2);

        assertEquals("Jack Price", student.getName());
        assertEquals(4.2, student.getGpa());
    }
}
