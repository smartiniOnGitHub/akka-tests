akka-tests - TODO
=================

TODO
----
- general: ensure that messages sent (that goes to deadLetters) are right (is it Akka to dispatch there unknown messages in actors ?), urgent ...
- general: (java, groovy, scala) ensure that clients are able to use remote actors, urgent ... wip
- general: update dependencies on latest Akka-2.3.x and ensure all works with it ...
- general: add utility methods to simplify interaction with actors, then maybe factorize them in the common sub-project ...
- general: in the sample actor, add a fake calculate code block (with inside an empty loop up to the given number) ...
- general: send/receive messages between different actors ...
- general: move some common resources (classes, config/support files, etc) in the common sub-project ...
- general: add samples to get ActorRef from ActorSelection using resolveOne ...

- akka-tests-groovy: Groovy scripts, check for differences (in behavior) if run from Groovy Console/Shell or from Groovy command-line ... wip
- akka-tests-groovy: use switch instead of multiple is/else if/else, for code more aligned to Groovy ...
- akka-tests-groovy: in messages, use @Immutable (since Groovy 2.0) ...
- akka-tests-groovy: in messages, use Groovy traits (since Groovy 2.3) ...
- akka-tests-groovy: use Groovy annotations for @TypeChecked and @CompileStatic (since Groovy 2.0) ...
- akka-tests-groovy: test with the same Groovy release used by Grails-3.0.x ...
- akka-tests-groovy: use log instead of println ...

- akka-tests-java: add shutdown hook, and update log messages ...

- akka-tests-scala: write it in a similar way to the Groovy version, to ensure there aren't problems in my sample code ...
- akka-tests-scala: write a Gradle task runScalaScript (or App), like that of the Groovy version ...

- add unit tests, using Akka-Testkit ...

- add a sub-project (in Java, then maybe even for other languages) for a simple actor server, extending Akka Bootable (note that other dependencies are needed, and classes must be deployed by copying generated jars in Akka standalone deploy folder) ...


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
- general: set dependencies on latest Akka-2.2.x and ensure all works with it (at least remoting from all subprojects) ... ok
- general: add samples to get ActorRef from ActorSelection (via Identify messages) ... done (but partial)
- general: empty sender (in messages to actors, via tell), use ActorRef.noSender() instead of null ... ok
- general: (java, groovy, scala) check if the client must run with remoting enabled even only for lookup/use remote actors ... yes (it seems)

- akka-tests-groovy: check how (if possible) to add .gsh (or .groovy) scripts and where to put them ... maybe in a dedicated folders like scripts, but Gradle builds need to be aware of this ... see later
- akka-tests-groovy: Groovy scripts, check if add dependencies to akka with Groovy @Grab annotations ... maybe in scripts
- akka-tests-groovy: remove in Groovy Application classes all code specific for running from a Groovy Console/Shell ... ok
- akka-tests-groovy: Remoting here doesn't work with Akka-2.2.4 and 2.3.9 ... in the meantime try from akka-tests-java ... no, it was a problem in my code ... ok
- akka-tests-groovy: test even with an updated (but stable) Groovy release, like latest 2.3.x, and latest 2.4.x ... ok
- akka-tests-groovy: test with the same Groovy release used by latest Grails-2.2.x, and then with latest Grails-2.4.x ... ok

- akka-tests-java: write it in a similar way to the Groovy version, to ensure there aren't problems in my sample code ... ok
- akka-tests-java: refactor code, to make it more class-oriented, add a shutdown hook with right log to console, etc ... ok (could be more structured, but for now it's good)


---------------
