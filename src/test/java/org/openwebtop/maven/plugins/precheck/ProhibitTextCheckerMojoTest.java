/**
 * Copyright (C) 2010  Jaehyeon Nam (dotoli21@gmail.com)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.openwebtop.maven.plugins.precheck;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.codehaus.plexus.util.DirectoryScanner;
import org.junit.Before;
import org.junit.Test;

import org.openwebtop.maven.plugins.precheck.prohibittext.ProhibitText;
import org.openwebtop.maven.plugins.precheck.prohibittext.ProhibitTextChecker;

/**
 * Prohibit text checker mojo test
 *
 * @author Jaehyeon Nam (dotoli21@gmail.com)
 * @since 2010. 5. 5.
 */
public class ProhibitTextCheckerMojoTest {
	private DirectoryScanner directoryScanner;
	private ProhibitTextChecker prohibitTextChecker;
	private ProhibitTextCheckerMojo prohibitTextCheckerMojo;

	@Before
	public void setUp() throws Exception {
		directoryScanner = mock(DirectoryScanner.class);
		prohibitTextChecker = mock(ProhibitTextChecker.class);

		prohibitTextCheckerMojo = new ProhibitTextCheckerMojo();
		prohibitTextCheckerMojo.setDirectoryScanner(directoryScanner);
		prohibitTextCheckerMojo.setProhibitTextChecker(prohibitTextChecker);
	}

	@Test
	public void testExecute() throws Exception {
		when(directoryScanner.getIncludedFiles()).thenReturn(new String[] {"test.html"});

		final File baseDirectory = FileUtils.toFile(ProhibitTextCheckerMojoTest.class.getResource("prohibittext"));
		final String[] errorString = new String[] {"error_string"};

		final ProhibitText prohibitText = new ProhibitText();
		prohibitText.setBasedir(baseDirectory);
		prohibitText.setProhibitTextPatterns(new String[] {"error_string"});

		prohibitTextCheckerMojo.setProhibitText(prohibitText);
		prohibitTextCheckerMojo.execute();

		final File file = FileUtils.toFile(ProhibitTextCheckerMojoTest.class.getResource("prohibittext/test.html"));
		verify(prohibitTextChecker, times(1)).check(file, errorString);
	}

}
