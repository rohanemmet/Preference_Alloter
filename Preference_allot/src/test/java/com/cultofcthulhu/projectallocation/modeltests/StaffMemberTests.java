package com.cultofcthulhu.projectallocation.modeltests;

import com.cultofcthulhu.projectallocation.models.StaffMember;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StaffMemberTests {

    @Test
    public void testConstructor() {
        StaffMember staffMember = new StaffMember(0, "Jack Price", "Gaming, Testing, Sleeping");

        assertEquals("Jack Price", staffMember.getName());
        assertEquals("Gaming, Testing, Sleeping", staffMember.getResearchInterests());
    }

}
