akka-tests - TODO
=================

TODO
----
- general: (java, groovy, scala) ensure that clients are able to use remote actors, urgent; start with lookup and send messages to, and no remote deployment for now ... wip
- general: after all works with Akka 2.3.x (so tag that release and maybe create a maintenance branch), update to Akka 2.4.x (and Java 8), and update even README file ... wip
- general: try the new Akka remoting protocol, based on Artery, as seen [hare](http://doc.akka.io/docs/akka/2.4/java/remoting-artery.html), since Akka-2.4.11 ...
- general: sample actors (GreetingActor, ProxyActor), refactor common code in BaseActor, not strictly required but could be a good practice to follow ... wip
- general: tests, comment general dependency on JUnit, and use junit only for Java subproject, and use spock-1.0.x for Groovy, and latest ScalaTest for Scala ...
- general: get host name/address from code (at least from environment variable) ...
- general: get startup argument (if given) to delay server shutdown ...
- general: add utility methods to simplify interaction with actors, then maybe factorize them in the common sub-project ...
- general: sample actors (GreetingActor, ProxyActor), make them extend BaseActor, not strictly required but could be a good practice to follow ...
- general: in the sample actor, add a fake calculate code block (with inside an empty loop up to the given number) ...
- general: send/receive messages between different actors ...
- general: move some common resources (classes, config/support files, etc) in the common sub-project ...
- general: send via Identify messages to actors, but better with Identify(path) with path of the remote actor, instead of Identify(null) ...
- general: add samples to get ActorRef from ActorSelection using resolveOne (and maybe give to it a Timeout(10000)) ...
- general: add an utility class to act as a factory for Akka Configurations, given some arguments ...

- akka-tests-groovy: Groovy scripts, check for differences (in behavior) if run from Groovy Console/Shell or from Groovy command-line ... wip
- akka-tests-groovy: use switch instead of multiple is/else if/else, for code more aligned to Groovy ...
- akka-tests-groovy: in messages, use @Immutable (since Groovy 2.0) ...
- akka-tests-groovy: in messages, use Groovy traits (since Groovy 2.3) ...
- akka-tests-groovy: use Groovy annotations for @TypeChecked and @CompileStatic (since Groovy 2.0) ...
- akka-tests-groovy: test with the same Groovy release used by latest Grails-3.x ...
- akka-tests-groovy: use log instead of println ...
- akka-tests-groovy: update dependencies to latest Scala-2.11.x, but to make it work with Groovy I need an updated (still work-in-progress) version of [groovytransforms](https://github.com/smartiniOnGitHub/groovytransforms) ...

- akka-tests-java: update deprecated code ... wip
- akka-tests-java: add shutdown hook, and update log messages ...

- akka-tests-scala: write it in a similar way to the Groovy version, to ensure there aren't problems in my sample code ...
- akka-tests-scala: write a Gradle task runScalaScript (or App), like that of the Groovy version ...

- various: add unit tests, using Akka-Testkit ...


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
- general: host address, test even with "0.0.0.0" ... ok, but not strictly required ("127.0.0.1" or "localhost") are good defaults in dev environment
- general: ensure that messages sent (that goes to deadLetters) are right (is it Akka to dispatch there unknown messages in actors ?), urgent ... yes
- general: get better info on remoting for java (current here means 2.3.9): http://doc.akka.io/docs/akka/current/java/remoting.html ... ok
- general: in Java API, to create actors, check if would be good to use this new Props( ... ) instead of Props.create( ... ) ... seems no
- general: the discussion on my problems to run remote actors is "[akka-user] Problems using a Remote Actor" at Akka User Group at Google Groups ... ok
- general: update Gradle wrapper to 2.8 or later ... ok but note that since Gradle 2.4 it's possible to do the same even with a command line like this: 'gradle wrapper --gradle-version 2.8'
- general: update dependencies to latest Akka-2.3.x (but not higher for now because since Akka-2.4.x is needed Java 8) but for now stay to Scala-2.10.x ... ok
- general: update dependencies, and repeat later ... ok, and force a refresh with 'gradle clean' and then 'gradle build --refresh-dependencies'
- general: update to latest Gradle and regenerate its wrapper ... ok, updated to Gradle 2.14.1 (last for 2.x), and then even with latest 3.1
- general: update to latest Groovy ... ok, updated to Groovy 2.4.7
- general: update to latest Scala ... ok, defined dependencies to latest releases both for 2.10.x and 2.11.x, but keep commented until really used
- general: switch to Scala 2.11.x ... ok, mainly because Scala-2.10.x is a little outdated now, and Scala-2.12 (that requires Java 8) is near to be released
- general: remove deprecations in code: "warning: [deprecation] actorFor(String) in ActorSystem has been deprecated" ... ok, removed related (old) code

- akka-tests-groovy: check how (if possible) to add .gsh (or .groovy) scripts and where to put them ... maybe in a dedicated folders like scripts, but Gradle builds need to be aware of this ... see later
- akka-tests-groovy: Groovy scripts, check if add dependencies to akka with Groovy @Grab annotations ... maybe in scripts
- akka-tests-groovy: remove in Groovy Application classes all code specific for running from a Groovy Console/Shell ... ok
- akka-tests-groovy: Remoting here doesn't work with Akka-2.2.4 and 2.3.9 ... in the meantime try from akka-tests-java ... no, it was a problem in my code ... ok
- akka-tests-groovy: test even with an updated (but stable) Groovy release, like latest 2.3.x, and latest 2.4.x ... ok
- akka-tests-groovy: test with the same Groovy release used by latest Grails-2.2.x, and then with latest Grails-2.4.x ... ok
- akka-tests-groovy: test with the same Groovy release used by latest Grails-3.x ... ok

- akka-tests-java: write it in a similar way to the Groovy version, to ensure there aren't problems in my sample code ... ok
- akka-tests-java: refactor code, to make it more class-oriented, add a shutdown hook with right log to console, etc ... ok (could be more structured, but for now it's good)
- akka-tests-java: make AkkaRemoteServer implements Bootable (from akka.kernel.Bootable), optional ... ok
- akka-tests-java: add ProxyActor, to act as a proxy for remote actors ... ok

- various: add a sub-project (in Java, then maybe even for other languages) for a simple actor server, extending Akka Bootable (note that other dependencies are needed, and classes must be deployed by copying generated jars in Akka standalone deploy folder) ... no, because as seen in [Migration Guide 2.3.x to 2.4.x - Akka Documentation](http://doc.akka.io/docs/akka/current/project/migration-guide-2.3.x-2.4.x.html) since Akka 2.4, Akka Microkernel is deprecated, so use instead other solutions as written in docs
- various: check (using extensions or pluggable protocols) if lookup remote actors even in broadcast, for example with cajo (using its rmi-based protocol and standard port 1198 tcp and udp) ... maybe later


---------------
