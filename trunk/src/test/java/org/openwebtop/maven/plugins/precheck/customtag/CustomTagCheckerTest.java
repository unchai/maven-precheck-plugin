package org.openwebtop.maven.plugins.precheck.customtag;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

public class CustomTagCheckerTest {
	private CustomTagChecker customTagChecker;

	@Before
	public void setUp() throws Exception {
		customTagChecker = new CustomTagChecker();
	}

	@Test
	public void testCheck() throws Exception {
		final File file = FileUtils.toFile(CustomTagCheckerTest.class.getResource("test.jsp"));
		customTagChecker.check(file);
	}

}
