akka-tests - TODO
=================

TODO
----
- general: set dependencies on latest Akka-2.2.x and ensure all works with it (at least remoting from all subprojects) ... wip
- general: update dependencies on latest Akka-2.3.x and ensure all works with it ...

- akka-tests-groovy: Groovy scripts, check for differences (in behavior) if run from Groovy Console/Shell or from Groovy command-line ... wip
- akka-tests-groovy: Remoting here doesn't work with Akka-2.2.4 and 2.3.9 ... ask to Akka guru, but in the meantime try from akka-tests-java ... wip
- akka-tests-groovy: use Groovy traits and @Immutable in messages ...
- akka-tests-groovy: test with an updated (but stable) Groovy release, like latest 2.3.x ...
- akka-tests-groovy: test with the same Groovy release used by latest Grails-2.2.x, and then with latest Grails-2.4.x ...
- akka-tests-groovy: use Groovy annotations for @TypeChecked and @CompileStatic ...
- akka-tests-groovy: test with the same Groovy release used by Grails-3.0.x ...

- akka-tests-java: refactor code, to make it more class-oriented, add a shutdown hook with right log to console, etc ...
- akka-tests-scala: write it in a similar way to the Groovy version, to ensure there aren't problems in my sample code ...
- akka-tests-scala: write a Gradle task runScalaScript (or App), like that of the Groovy version ...

- add unit tests, using Akka-Testkit ...


- etc ...

---------------


DONE
----
- general: setup initial structure, and initial subprojects with latest Gradle (stable) ... ok
- general: generate eclipse project files, but add to ignore list in source control ... ok
- general: add license header in all sources ... ok
- general: add BUILD file ... ok
- general: setup main subprojects (for Java, Groovy, Scala) ... ok
- general: check if add dependencies to akka in the general (or in common) gradle build files ... for now in general (root) and maybe later move them in common ... ok
- general: in subprojects add running scripts or better in Gradle builds add some info on how to running them from Gradle ... add custom tasks in Gradle scripts ...  ok
- general: in Gradle tasks, put a description on my tasks, and on what can be run, for simpler usage ... ok

- akka-tests-groovy: check how (if possible) to add .gsh (or .groovy) scripts and where to put them ... maybe in a dedicated folders like scripts, but Gradle builds need to be aware of this ... see later
- akka-tests-groovy: Groovy scripts, check if add dependencies to akka with Groovy @Grab annotations ... maybe in scripts
- akka-tests-groovy: remove in Groovy Application classes all code specific for running from a Groovy Console/Shell ... ok

- akka-tests-java: write it in a similar way to the Groovy version, to ensure there aren't problems in my sample code ... ok


---------------
