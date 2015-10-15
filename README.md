# Maven precheck plugin #
### Overview ###
This plugin is a collection of several prechecking tasks before build project.

### Goals Overview ###
This plugin has two goals:
- `precheck:prohibittext` is used to check prohibit text in target files. If prohibit text is exist in target files, maven build will be fail.
- `precheck:webwork` is used to check webwork configuration. This task will check the following three rules.
  - Duplicate package name
  - Duplicate package namespace
  - Duplicate action name in package
- `precheck:customtag` is used to check undefined custom tag in markup.
- `precheck:environment` is used to check application environment-specific each build phase(ex: local, devel, release).


### Examples ###
To provide you with better understanding on some usages of the Maven Precheck Plugin, you can take a look into the following examples:

#### Checking prohibit text ####
The following configuration snippet would check all files in the directory `web` has prohibit text `<img src="http://localhost/title.jpg" />`
```
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
          <prohibitTextPattern><![CDATA[<img[^>]*src="http://localhost[^>]*>]]></prohibitTextPattern>
          <prohibitTextPattern><![CDATA[<script[^>]*src="http://localhost[^>]*]]></prohibitTextPattern>
        </prohibitTextPatterns>
      </prohibitText>
    </prohibitTexts>
  </configuration>
</plugin>
```

#### Checking webwork configuration ####
The following configuration snippet would check webwork configuration.
```
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
```

#### Checking custom tag ####
The following configuration snippet would check custom tag.
```
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
```

#### Checking environment ####
The following configuration snippet would check environment-specific each build phase.

For example, there is 3 build phase likes `local`, `devel`, `releae`.
Surely, you don't want your `local` and `devel` DB's IP in "release" build phase!

This goal will check to make sure `local` and `devel`'s specific strings not in `release` build phase.
```
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
        </checkTarget>
        <checkSource>
          <basedir>src/main/resources</basedir>
          <includes>
            <include>local/**/**</include>
            <include>test/**/**</include>
            <include>dev/**/**</include>
          </includes>
        </checkSource>
      </environment>
    </environments>
  </configuration>
</plugin>
```

## Notice

Maven central repository processed. (http://repo1.maven.org/maven2/org/openwebtop/maven/plugins/maven-precheck-plugin)
