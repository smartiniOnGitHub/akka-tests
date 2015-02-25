/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package akka_tests.java.server;

import akka_tests.java.actor.*;
import akka_tests.java.command.*;
import akka_tests.java.message.*;

// import org.junit.*;

import akka.actor.*;
// import akka.testkit.*;  // only for tests

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import scala.concurrent.duration.Duration;


/**
 * Simple Java server application for creating some Akka Actors and make them reachable from other (remote) processes
 */
class AkkaRemoteServer {

	// Utility method
	public static final void sleep(long msec) {
        assert msec >= 0 : "Sleep time must be 0 or positive.";
		try {
			Thread.sleep(msec);
		} catch ( java.lang.InterruptedException ie) {
			System.err.println(ie);
		}
	}

	public static void main(String[] args) {
		System.out.println("Application: main, start a simple server console application for creating some Akka Actors and make them reachable from other (remote) processes\n");

		// setup phase
		System.out.println("setup: start at " + new java.util.Date() + ".");

		// inline Akka configuration script, to enable publishing actors available in remote, and with some useful settings for a dev environment
        // note: use here (even if not strictly necessary) the Java-like syntax for multiline strings that in Groovy works ...
		String akkaConfig = "" +
            "akka {\n" +
            "    loglevel = \"INFO\"\n" +
            // "    log-config-on-start = on\n" +
            // "    daemonic = on # workaround to not keep it running here\n" +
            "    actor {\n" +
            "        provider = \"akka.remote.RemoteActorRefProvider\"\n" +
            "    }\n" +
            "    remote {\n" +
            "        enabled-transports = [\"akka.remote.netty.tcp\"]\n" +
            "        netty.tcp {\n" +
            "            hostname = \"127.0.0.1\"\n" +
            "            # Server, listen on default Akka tcp port (2552)\n" +
            "            port = 2552\n" +
            "        }\n" +
            "        log-sent-messages = on\n" +
            "        log-received-messages = on\n" +
            "        log-remote-lifecycle-events = on\n" +
            "        log-frame-size-exceeding = on\n" +
            "        # log-buffer-size-exceeding = 50000\n" +
            "    }\n" +
            "}";
		System.out.println("Akka Config: " + akkaConfig);

		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		System.out.println("using Java ClassLoader: " + cl);
		System.out.println("using Akka version: " + ActorSystem.Version());
		
		Config config = // ConfigFactory.load();  // load from application.conf
			ConfigFactory.parseString(akkaConfig);  // parse the configuration inside the multi-line string

		// global actor system to start here
		String remotableSystemName = "RemoteActorSystem";
		final ActorSystem system = // ActorSystem.create(remotableSystemName);
			// ActorSystem.create(remotableSystemName, config);// default version, good here
			// ActorSystem.create(remotableSystemName, config, cl);  // set a classloader
			ActorSystem.create(remotableSystemName, config);  // do not set a classloader when run from Gradle ...
		System.out.println("system: " + system);
		// System.out.println("system configuration: ");
		// system.logConfiguration();  // log the real configuration of the system (could be different than akkaConfig) ...
		Props       props  = 
			// new Props(GreetingActor.class);  // deprecated ...
			Props.create(GreetingActor.class);  // ok ...
		System.out.println("props: " + props);
		sleep(500);  // workaround, mainly for flushing console output ...
		System.out.println("setup: end at " + new java.util.Date() + ".");

		// create instance for some actors
		String remotableActorName = "greetingActor";  // "greeting_actor";
		ActorRef actor = system.actorOf(props, remotableActorName);
		System.out.println("Get Actor Reference to GreetingActor: " + actor);
		// TODO: start more actors here ...

		sleep(500);  // workaround, mainly for flushing console output ...
		System.out.println("setup: end at " + new java.util.Date() + ".");


		System.out.println("check: start at " + new java.util.Date() + ".");
		System.out.println("Actor System instance: " + system);
		assert system != null;
		System.out.println("Actor System configuration: " + config);
		assert config != null;
		// get a new reference to our greeting actor, to ensure all is good
		System.out.println("props: " + props);
		assert props != null;
		actor = system.actorOf(props);
		System.out.println("Actor Reference instance is: " + actor);
		assert actor != null;
		// send some test messages to the actor
		actor.tell(new Identify(null), null);  // send a standard Identify message, so the sender actor will then receive a standard ActorIdentity response ...
		actor.tell(new Greeting("Test Greeting"), null);
		actor.tell(new String("Test String"), null);
		actor.tell(new GenericMessage<String>("simple generic message with a String"), null);
		sleep(500);  // workaround, mainly for flushing console output ...
		System.out.println("check: end at " + new java.util.Date() + ".");


		System.out.println("check (remote): start at " + new java.util.Date() + ".");
// TODO: check if must be done from another system ...
		System.out.println("Actor System instance: " + system);
		assert system != null;
		// get a selection to our remote greeting actor
		String remoteSystemName = "RemoteActorSystem";
		String remoteBasePath = "akka.tcp://" + remoteSystemName + "@127.0.0.1:2552/user/";
		System.out.println("remote actor system base path: " + remoteBasePath);
		String remoteActorName = "greetingActor";  // "greeting_actor";
		ActorSelection selection = system.actorSelection(remoteBasePath + remoteActorName);
		assert selection != null;
		selection.tell(new Identify(null), null);  // send a standard Identify message, so the sender actor will then receive a standard ActorIdentity response ...
		selection.tell("Test Remote", null);
		sleep(500);  // workaround, mainly for flushing console output ...
		System.out.println("check (remote): end at " + new java.util.Date() + ".");


		System.out.println("\nServer ready ...");


		// sleep(500);  // workaround, mainly for flushing console output ...
		// system.shutdown();
		// sleep(500);  // workaround, mainly for flushing console output ...


// TODO: (later) make a little cleanup to move features into methods ...


		System.out.println("\nApplication: main, end at " + new java.util.Date() + ".");  // this is really the end of execution, when daemonic = on , otherwise a shutdown hook should handle the end of execution, and change the message here ...
	}

}
