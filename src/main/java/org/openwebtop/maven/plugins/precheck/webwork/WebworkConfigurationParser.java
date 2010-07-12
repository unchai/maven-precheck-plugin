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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

import org.openwebtop.maven.plugins.precheck.webwork.model.WebworkAction;
import org.openwebtop.maven.plugins.precheck.webwork.model.WebworkPackage;

/**
 * Webwork configuration parser
 *
 * @author Jaehyeon Nam (dotoli21@gmail.com)
 * @since 2010. 5. 5.
 */
public class WebworkConfigurationParser {

	/**
	 * Parse file to webwork configuration
	 *
	 * @param webworkConfigurationFile Webwork configuration file
	 * @return Webwork configuration package list
	 * @throws IOException IOException
	 * @throws SAXException SAXException
	 */
	@SuppressWarnings("unchecked")
	public List<WebworkPackage> getWebworkPackages(File webworkConfigurationFile) throws IOException, SAXException {
		final Digester digester = new Digester();
		digester.addObjectCreate("xwork", ArrayList.class);
		digester.addObjectCreate("xwork/package", WebworkPackage.class);
		digester.addSetProperties("xwork/package", "name", "name");
		digester.addSetProperties("xwork/package", "namespace", "namespace");
		digester.addObjectCreate("xwork/package/action", WebworkAction.class);
		digester.addSetProperties("xwork/package/action", "name", "name");
		digester.addSetProperties("xwork/package/action", "class", "className");
		digester.addSetNext("xwork/package/action", "addAction");
		digester.addSetNext("xwork/package", "add");

		return (List<WebworkPackage>)digester.parse(webworkConfigurationFile);
	}

}
