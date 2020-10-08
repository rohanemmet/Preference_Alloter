package com.cultofcthulhu.projectallocation.modeltests;

import com.cultofcthulhu.projectallocation.models.Solution;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class SolutionTests {

    @Test
    public void testConstructor() {
        Map<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i < 10; i++) {
            map.put(i, i);
        }
        Integer[] array = new Integer[] {0,1,2,3,4};
        Solution solution = new Solution(map, array);

        assertEquals(map, solution.getSolution());
    }

    @Test
    public void testChange() {
        Map<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i < 10; i++) {
            map.put(i, i);
        }
        Integer[] array = new Integer[] {0,1,2,3,4};
       Solution solution = new Solution(map, array);
       Solution solution1 = new Solution(map, array);
       solution1.change();

       assertNotEquals(solution.getStudentProjectAssignmentOrder(), solution1.getStudentProjectAssignmentOrder());
    }
}
