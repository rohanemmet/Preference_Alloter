package com.cultofcthulhu.projectallocation;

import com.cultofcthulhu.projectallocation.exceptions.ParseException;
import com.cultofcthulhu.projectallocation.models.data.ProjectDAO;
import com.cultofcthulhu.projectallocation.models.data.StudentProjectDAO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileParserTest {

    @Test
    public void testStaffParse() throws ParseException, IOException {
        FileParser parser = new FileParser();

        parser.parseStaff(new File("test-files/Staff/ShouldWork.csv"));
        parser.parseStaff(new File("test-files/Staff/ShouldWork.tsv"));

        Exception exception = assertThrows(ParseException.class, () -> {
           parser.parseStaff(new File("test-files/Staff/WontWork.csv"));
        });
        String expectedMessage = "has an incorrect number of fields";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Autowired
    private StudentProjectDAO studentProjectDAO;
    @Autowired
    private ProjectDAO projectDAO;
    @Test
    public void testStudentParse() throws ParseException, IOException {
        FileParser StudentParsingWorks = new FileParser();
        StudentParsingWorks.parseStudents(new File("test-files/Student/ShouldWork.csv"), projectDAO, studentProjectDAO);
        StudentParsingWorks.parseStudents(new File("test-files/Student/ShouldWork.tsv"), projectDAO, studentProjectDAO);

        Exception exception = assertThrows(ParseException.class, () -> {
            StudentParsingWorks.parseStudents(new File("test-files/Student/IncorrectFields.csv"), projectDAO, studentProjectDAO);
        });
        String actualMessage = exception.getMessage();
        String expectedMessage = "has an incorrect number of fields";
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
