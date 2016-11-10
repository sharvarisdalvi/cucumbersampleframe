Cucumber-Java Test Framework
============================

This framework is to setup cucumber-Java page object based automation testing. It has 2 modules test-core & functional-tests

* test-core module contains all basic selenium seetiings like drivers, testcase management etc.
* functional-test module contains page object classes, step definition classes & feature files.

How to setup & run
------------------

Prerequisite:
* JDK 1.7
* Maven
* IntelliJ Idea with cucumber for Java plugin
* JDK & Maven path should be set

Steps to run from IntelliJ:
* Clone project
* Import project in IntelliJ with POM file (wait for all dependencies to resolved)
* Right click on RunTest & select run


Steps to run from Commandline:
* Open commandline & reach till cloned project directory
* mvn clean verify
