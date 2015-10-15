# Maven precheck plugin

This plugin is a collection of several prechecking tasks before build project.

## Goals Overview

This plugin has two goals:
- `precheck:prohibittext` is used to check prohibit text in target files. If prohibit text is exist in target files, maven build will be fail.
```
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
```

- `precheck:webwork` is used to check webwork configuration. This task will check the following three rules.
  - Duplicate package name
  - Duplicate package namespace
  - Duplicate action name in package
```
  <build>
    <plugins>
      ...
      <plugin>
        <groupId>org.openwebtop.maven.plugins</groupId>
        <artifactId>maven-precheck-plugin</artifactId>
        <executions>
          <execution>
            <id>webwork</id>
            <phase>validate</phase>
            <goals>
              <goal>webwork</goal>
            </goals>
          </execution>
        </executions>
        <configuration>
          <webworkConfiguration>
            <basedir>src/main/resources/common</basedir>
            <includes>
              <include>xwork*.xml</include>
            </include>
          </webworkConfiguration>
        </configuration>
      </plugin>
    </plugins>
  </build>
  ```

- `precheck:customtag` is used to check undefined custom tag in markup.
```
  <build>
    <plugins>
      ...
      <plugin>
        <groupId>org.openwebtop.maven.plugins</groupId>
        <artifactId>maven-precheck-plugin</artifactId>
        <executions>
          <execution>
            <id>customtag</id>
            <phase>validate</phase>
            <goals>
              <goal>customtag</goal>
            </goals>
          </execution>
        </executions>
        <configuration>
          <customTag>
            <basedir>web</basedir>
            <includes>
              <include>**/*.jsp</include>
            </includes>
          </customTag>
        </configuration>
      </plugin>
    </plugins>
  </build>
```
- `precheck:environment` is used to check application environment-specific each build phase(ex: local, devel, release).
```
  <build>
    <plugins>
      ...
      <plugin>
        <groupId>org.openwebtop.maven.plugins</groupId>
        <artifactId>maven-precheck-plugin</artifactId>
        <executions>
          <execution>
            <id>environment</id>
            <phase>validate</phase>
            <goals>
              <goal>prohibittext</goal>
            </goals>
          </execution>
        </executions>
        <configuration>
          <environments>
            <environment>
            <description>Check IP Address</description>
              <extractRegExp><![CDATA[\b(?:\d{1,3}\.){3}\d{1,3}\b]]></extractRegExp>
              <checkTarget>
                <basedir>src/main/resources/real</basedir>
                <includes>
                  <include>**/**</include>
                </includes>
                <excludes>
                  <exclude>**/.svn/**</exclude>
                </excludes>
              </checkTarget>
              <checkSource>
                <basedir>src/main/resources</basedir>
                <includes>
                  <include>local/**/**</include>
                  <include>test/**/**</include>
                  <include>dev/**/**</include>
                </includes>
                <excludes>
                  <exclude>**/.svn/**</exclude>
                </excludes>
              </checkSource>
            </environment>
          </environments>
        </configuration>
      </plugin>
    </plugins>
  </build>
```

## Examples

To provide you with better understanding on some usages of the Maven Precheck Plugin, you can take a look into the following examples:

- Checking prohibit text
- Checking webwork configuration
- Checking custom tag
- Checking environment

## Notice

Maven central repository processed. (http://repo1.maven.org/maven2/org/openwebtop/maven/plugins/maven-precheck-plugin)
