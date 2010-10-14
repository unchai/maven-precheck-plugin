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
package org.openwebtop.maven.plugins.precheck.webwork;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import org.openwebtop.maven.plugins.precheck.webwork.model.WebworkPackage;

/**
 * Webwork configuration parser test
 *
 * @author Jaehyeon Nam (dotoli21@gmail.com)
 * @since 2010. 5. 5.
 */
public class WebworkConfigurationParserTest {
	private WebworkConfigurationParser parser;

	@Before
	public void setUp() throws Exception {
		parser = new WebworkConfigurationParser();
	}

	@Test
	public void testGetWebworkPackages() throws Exception {
		final File file = FileUtils.toFile(WebworkConfigurationParserTest.class.getResource("xwork.xml"));

		final List<WebworkPackage> webworkPackages = parser.getWebworkPackages(file);
		assertThat(webworkPackages, is(not(nullValue())));
		assertThat(webworkPackages.size(), is(3));

		final WebworkPackage webworkPackage1 = webworkPackages.get(0);
		assertThat(webworkPackage1, is(not(nullValue())));
		assertThat(webworkPackage1.getName(), is("org.openwebtop.maven.plugins.precheck"));
		assertThat(webworkPackage1.getNamespace(), is(nullValue()));
		assertThat(CollectionUtils.isEmpty(webworkPackage1.getActions()), is(true));

		final WebworkPackage webworkPackage2 = webworkPackages.get(1);
		assertThat(webworkPackage2, is(not(nullValue())));
		assertThat(webworkPackage2.getName(), is("org.openwebtop.maven.plugins.precheck.index"));
		assertThat(webworkPackage2.getNamespace(), is("/"));
		assertThat(CollectionUtils.isNotEmpty(webworkPackage2.getActions()), is(true));
		assertThat(webworkPackage2.getActions().size(), is(1));
		assertThat(webworkPackage2.getActions().get(0).getName(), is("index"));
		assertThat(webworkPackage2.getActions().get(0).getClassName(), is("org.openwebtop.maven.plugins.precheck.index.IndexAction"));

		final WebworkPackage webworkPackage3 = webworkPackages.get(2);
		assertThat(webworkPackage3, is(not(nullValue())));
		assertThat(webworkPackage3.getName(), is("org.openwebtop.maven.plugins.precheck.board"));
		assertThat(webworkPackage3.getNamespace(), is("/board"));
		assertThat(CollectionUtils.isNotEmpty(webworkPackage3.getActions()), is(true));
		assertThat(webworkPackage3.getActions().size(), is(2));
		assertThat(webworkPackage3.getActions().get(0).getName(), is("list"));
		assertThat(webworkPackage3.getActions().get(0).getClassName(), is("org.openwebtop.maven.plugins.precheck.board.BoardListAction"));
		assertThat(webworkPackage3.getActions().get(1).getName(), is("read"));
		assertThat(webworkPackage3.getActions().get(1).getClassName(), is("org.openwebtop.maven.plugins.precheck.board.BoardReadAction"));
	}

}
