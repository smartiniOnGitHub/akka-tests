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
		System.out.println("Application: Start a simple server console application for creating some Akka Actors and make them reachable from other (remote) processes\n");

		// setup phase
		System.out.println("setup: start at " + new java.util.Date() + ".");

		// inline Akka configuration script, to enable publishing actors available in remote, and with some useful settings for a dev environment
		String akkaConfig = "akka {\n    loglevel = 'DEBUG'\n    daemonic = on\n    actor {\n    provider = 'akka.remote.RemoteActorRefProvider'\n    }\n    remote {\n    enabled-transports = ['akka.remote.netty.tcp']\n    netty.tcp {\n    hostname = '127.0.0.1'\n    port = 2552\n    }\n    log-sent-messages = on\n    log-received-messages = on\n    log-remote-lifecycle-events = on\n    log-frame-size-exceeding = on\n    }\n    }";

		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		System.out.println("using Java ClassLoader: " + cl);
		System.out.println("using Akka version: " + ActorSystem.Version());

		// global actor system to start here
		final String remoteSystemName = "RemoteActorSystem";
		final ActorSystem system = // ActorSystem.create(remoteSystemName);
			// ActorSystem.create(remoteSystemName, ConfigFactory.load(akkaConfig));
			// ActorSystem.create(remoteSystemName, ConfigFactory.load(akkaConfig), cl);  // set a classloader
			ActorSystem.create(remoteSystemName, ConfigFactory.load(akkaConfig));  // do not set a classloader when run from Gradle ...
		System.out.println("using Akka Config: " + akkaConfig);
		System.out.println("system: " + system);
		Props       props  = Props.create(GreetingActor.class);
		System.out.println("props: $props");
		sleep(500);  // workaround, mainly for flushing console output ...
		System.out.println("setup: end at " + new java.util.Date() + ".");

		// create instance for some actors
		ActorRef actor = system.actorOf(props, "greeting_actor");
		System.out.println("Get Actor Reference to GreetingActor: " + actor);
		// TODO: start more actors here ...

		sleep(500);  // workaround, mainly for flushing console output ...
		System.out.println("setup: end at " + new java.util.Date() + ".");


		System.out.println("check: start");
		System.out.println("Actor System instance: $system");
		assert system != null;
		// get a reference to our greeting actor
		System.out.println("props: " + props);
		assert props != null;
		actor = system.actorOf(props);
		System.out.println("Actor Reference instance is: " + actor);
		assert actor != null;
		// send some test messages to the actor
		actor.tell(new Greeting("Test Greeting"), null);
		assert actor != null;
		actor.tell(new String("Test String"), null);
		assert actor != null;
		actor.tell(new GenericMessage<String>("simple generic message with a String"), null);
		assert actor != null;
		sleep(500);  // workaround, mainly for flushing console output ...
		System.out.println("check: end at " + new java.util.Date() + ".");


		// system.tell("Start", null)  // TODO: temp ...
		System.out.println("Server ready ...");


		/*
		// TODO: make this application run, at least for one minute ...
		sleep 10000  // wait a little, to see if remote connections are accepted in the mean time ...
		// system.awaitTermination()  // TODO: check if needed ...
		 */

		/*
		// workaround, to keep this script running until user write a line of text
		sleep 500  // workaround, mainly for flushing console output ...
		System.out.println("\nHit ENTER to exit ...")
		System.out.println("(note that when running in Groovy Console, the input is being read from the text console in the background)")
		def quit = System.console()?.readLine()  // safer, when running in non-interactive mode (default) from Gradle ...

		system.shutdown();
		 */
		sleep(500);  // workaround, mainly for flushing console output ...


// TODO: (later) make a little cleanup to move features into methods ...


		System.out.println("\nApplication: execution end at " + new java.util.Date() + ".");
	}

}
