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
package akka_tests.java.client;

import akka_tests.java.actor.*;
import akka_tests.java.command.*;
import akka_tests.java.message.*;

// import org.junit.*;

import akka.actor.*;
// import akka.testkit.*;  // only for tests

import com.typesafe.config.ConfigFactory;

import scala.concurrent.duration.Duration;


/**
 * Simple Java client application for using (consuming) some Akka Actors published by the (remote) server application
 */
class AkkaRemoteClient {

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
		System.out.println("Application: Start a simple console application for using (consuming) some Akka Actors published by the (remote) server application (it must be running)\n");

		// setup phase
		System.out.println("setup: start at " + new java.util.Date() + ".");

		// inline Akka configuration script, to enable looking for remote actors, and with some useful settings for a dev environment
		String akkaConfig = 
			  "akka {\n"
			+ "    loglevel = \"DEBUG\"\n"
			+ "    actor {\n"
			+ "        provider = \"akka.remote.RemoteActorRefProvider\"\n"
			+ "    }\n"
			+ "    remote {\n"
			+ "        netty.tcp {\n"
			+ "            hostname = \"127.0.0.1\"\n"
			+ "            # Client, use a different port than server (2552)\n"
			+ "            # port = 2553\n"
			+ "            port = 0\n"
			+ "        }\n"
			+ "        log-sent-messages = on\n"
			+ "        log-received-messages = on\n"
			+ "    }\n"
			+ "}";

		System.out.println("using Akka version: " + ActorSystem.Version());

		// global actor system to start here
		final String localSystemName = "LookupActorSystem"; // "RemoteActorSystem-Client";
		final String remoteSystemName = "RemoteActorSystem";
		final String remoteBasePath = "akka.tcp://RemoteActorSystem@127.0.0.1:2552/user/";
		System.out.println("remote actor system base path: " + remoteBasePath);
		final String remoteActorName = "greetingActor";  // "greeting_actor";

		final ActorSystem system = // ActorSystem.create(localSystemName);
			// ActorSystem.create(localSystemName, ConfigFactory.load(akkaConfig));
			ActorSystem.create(localSystemName, ConfigFactory.load(akkaConfig));  // do not set another classloader when run from Gradle ...
			// ActorSystem.create(localSystemName);  // simplified version, good the same for a local system, using defaults
		System.out.println("using Akka Config: " + akkaConfig);
		System.out.println("system: " + system);
		sleep(500);  // workaround, mainly for flushing console output ...
		System.out.println("setup: end at " + new java.util.Date() + ".");

		System.out.println("check: start");
		System.out.println("Actor System instance: " + system);
		assert system != null;

		sleep(500);  // workaround, mainly for flushing console output ...
		System.out.println("check: end at " + new java.util.Date() + ".");
		System.out.println("Client ready ...");

		// TODO: make some call ...
		// get reference and then call some remote actors

		// TODO: ... check settings, and then try to simplify url for actor selection (and keep the full version commented) ...

		// TODO: create an actor in the  remote system (changes required even in config file) ...
		// final ActorRef actor = system.actorOf(Props.create(GreetingActor.class, remoteBasePath), remoteActorName);

		/*
		// lookup remote actor, but in a deprecated way ...
		ActorRef actorRemote = system.actorFor(remoteBasePath + remoteActorName);  // TODO: check if it's still the right way ...
		System.out.println("Get Actor Reference to remote GreetingActor: " + actorRemote);
		assert actorRemote != null;
		actorRemote.tell("Test String", null);
		assert actorRemote != null;
		 */


		// lookup remote actor ...
		ActorSelection selection = // system.actorSelection(remoteBasePath + remoteActorName);
			system.actorSelection(remoteBasePath + remoteActorName);  // TODO: check if/how to do this but with context ...
		System.out.println("Get Actor Selection to remote GreetingActor: " + selection);
		assert selection != null;
		selection.tell("Test String", null);
		assert selection != null;

		// TODO: get ActorRef actor from selection, sending a (via identify or similar) message to the selection and use the getSender reference of the reply from the actor ...

		/*
		// send some test messages to the actor
		actor.tell(new Greeting("Test Greeting"), null);
		assert actor != null;
		actor.tell(new String("Test String"), null);
		assert actor != null;
		actor.tell(new GenericMessage<String>("simple generic message with a String"), null);
		assert actor != null;
		 */

		system.shutdown();
		sleep(500);  // workaround, mainly for flushing console output ...


// TODO: (later) make a little cleanup to move features into methods ...


		System.out.println("\nApplication: execution end at " + new java.util.Date() + ".");
	}

}
