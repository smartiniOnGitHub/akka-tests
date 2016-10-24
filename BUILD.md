System Requirements
===================

* JDK 1.8 or greater (http://www.oracle.com/technetwork/java/)
* Gradle 3.1 or greater (http://gradle.org/)

Project Set-Up
==============

* Ensure that Gradle is on your path (set the PATH environment variable accordingly)


Building Akka-tests
===================

* To clean and refresh dpependencies (optional):

		$ gradle clean --refresh-dependencies

* To compile all source files into binary class files:

		$ gradle build

* To generate binary packages (one per subproject)

		$ gradle assemble

* To generate Javadoc

		$ gradle javadoc

* To generate Eclipse project files (optional)

		$ gradle eclipse


Note
====

None.


---
