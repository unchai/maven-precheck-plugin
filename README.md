# Maven precheck plugin

This plugin is a collection of several prechecking tasks before build project.

## Goals Overview

This plugin has two goals:
- `precheck:prohibittext` is used to check prohibit text in target files. If prohibit text is exist in target files, maven build will be fail.
`
  <build>
    <plugins>
      ...
      <plugin>
        <groupId>org.openwebtop.maven.plugins</groupId>
        <artifactId>maven-precheck-plugin</artifactId>
        <executions>
          <execution>
            <id>prohibittext</id>
            <phase>validate</phase>
            <goals>
              <goal>prohibittext</goal>
            </goals>
          </execution>
        </executions>
        <configuration>
          <prohibitTexts>
            <prohibitText>
              <basedir>web</basedir>
              <includes>
                <include>**/**</include>
              </includes>
              <prohibitTextPatterns>
                <prohibitTextPattern>
                   <![CDATA[<img[^>]*src="http://localhost[^>]*>]]>
                </prohibitTextPattern>
                <prohibitTextPattern>
                  <![CDATA[<script[^>]*src="http://localhost[^>]*]]>
                </prohibitTextPattern>
              </prohibitTextPatterns>
            </prohibitText>
          </prohibitTexts>
        </configuration>
      </plugin>
    </plugins>
  </build>
`

- `precheck:webwork` is used to check webwork configuration. This task will check the following three rules.
  - Duplicate package name
  - Duplicate package namespace
  - Duplicate action name in package
- `precheck:customtag` is used to check undefined custom tag in markup.
- `precheck:environment` is used to check application environment-specific each build phase(ex: local, devel, release).

## Examples

To provide you with better understanding on some usages of the Maven Precheck Plugin, you can take a look into the following examples:

- Checking prohibit text
- Checking webwork configuration
- Checking custom tag
- Checking environment

## Notice

Maven central repository processed. (http://repo1.maven.org/maven2/org/openwebtop/maven/plugins/maven-precheck-plugin)
