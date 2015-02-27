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

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import scala.concurrent.duration.Duration;


/**
 * Simple Java client application for using (consuming) some Akka Actors published by the (remote) server application
 */
class AkkaRemoteClient {

	// useful reference to empty sender actor, use this instead of null ...
	private static final ActorRef ACTOR_NO_SENDER = ActorRef.noSender();  // = null;


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
		System.out.println("Application: main, start a simple console application for using (consuming) some Akka Actors published by the (remote) server application (it must be running)\n");

		// setup phase
		System.out.println("setup: start at " + new java.util.Date() + ".");

		// inline Akka configuration script, to enable looking for remote actors, and with some useful settings for a dev environment
		// if the client run on the same host of the server (default port is 2552), be sure to use a different port like 2553 or 0 (random) ...
        // note: use here (even if not strictly necessary) the Java-like syntax for multiline strings that in Groovy works ...
		String akkaConfigClient = "" +
            "akka {\n" +
			"    loglevel = \"INFO\"\n" +
			// "    log-config-on-start = on\n" +
			"    actor.provider = \"akka.remote.RemoteActorRefProvider\"\n" +  // use the short version for nested properties, just to show its usage ...
			// "    remote.netty.tcp.hostname=\"127.0.0.1\"\n"
			"    remote.netty.tcp.port = 0\n" +  // set random port, useful when running the client on the same host of an already running  server ...
			"}";
		System.out.println("Akka Config: " + akkaConfigClient);

		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		System.out.println("using Java ClassLoader: " + cl);
		System.out.println("using Akka version: " + ActorSystem.Version());

		Config config = // ConfigFactory.load();  // load from application.conf
			ConfigFactory.parseString(akkaConfigClient);  // parse the configuration inside the multi-line string

		// global actor system to start here
		String localSystemName = "LookupActorSystem"; // "RemoteActorSystem-Client";
		String remoteSystemName = "RemoteActorSystem";
		String remoteBasePath = "akka.tcp://" + remoteSystemName + "@127.0.0.1:2552/user/";
		System.out.println("remote actor system base path: " + remoteBasePath);
		String remoteActorName = "greetingActor";  // "greeting_actor";

		final ActorSystem system = // ActorSystem.create(localSystemName);  // default version, good the same but only for a local system, using default settings
			// ActorSystem.create(localSystemName, config);  // default version, good here
			// ActorSystem.create(localSystemName, config, cl);  // set a classloader
			ActorSystem.create(localSystemName, config);  // do not set another classloader when run from Gradle ...
		System.out.println("system: " + system);
		sleep(500);  // workaround, mainly for flushing console output ...
		System.out.println("setup: end at " + new java.util.Date() + ".");

		System.out.println("check: start");
		System.out.println("Actor System instance: " + system);
		assert system != null;

		sleep(500);  // workaround, mainly for flushing console output ...
		System.out.println("check: end at " + new java.util.Date() + ".");
		System.out.println("Client ready ...");


		// get reference and then call some remote actors

		// lookup remote actor ...
		ActorSelection selection = // system.actorSelection(remoteBasePath + remoteActorName);
			system.actorSelection(remoteBasePath + remoteActorName);
		System.out.println("Get Actor Selection to remote GreetingActor: " + selection);
		assert selection != null;
		// send some test messages to the actor
		selection.tell(new Identify(null), ACTOR_NO_SENDER);  // send a standard Identify message, so the sender actor will then receive a standard ActorIdentity response ...
		selection.tell("Test String", ACTOR_NO_SENDER);
		selection.tell(new Greeting("Test Greeting"), ACTOR_NO_SENDER);
		selection.tell(new String("Test String"), ACTOR_NO_SENDER);
		selection.tell(new GenericMessage<String>("simple generic message with a String"), ACTOR_NO_SENDER);


		sleep(500);  // workaround, mainly for flushing console output ...
		system.shutdown();
		sleep(500);  // workaround, mainly for flushing console output ...


// TODO: (later) make a little cleanup to move features into methods ...


		System.out.println("\nApplication: main, end at " + new java.util.Date() + ".");
	}

}
