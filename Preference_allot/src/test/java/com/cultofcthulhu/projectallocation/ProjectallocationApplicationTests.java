package com.cultofcthulhu.projectallocation;

import com.cultofcthulhu.projectallocation.modeltests.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		ProjectTests.class,
		SolutionTests.class,
		StaffMemberTests.class,
		StudentTests.class,
		FileParserTest.class
})
public class ProjectallocationApplicationTests {

}
