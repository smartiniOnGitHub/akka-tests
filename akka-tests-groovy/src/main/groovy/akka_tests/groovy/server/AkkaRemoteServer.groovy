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
package akka_tests.groovy.server

import akka_tests.groovy.actor.*
import akka_tests.groovy.command.*
import akka_tests.groovy.message.*

// import org.junit.*

import akka.actor.*
// import akka.testkit.*  // only for tests

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory

import scala.concurrent.duration.Duration


/**
 * Simple Groovy server application for creating some Akka Actors and make them reachable from other (remote) processes
 */
class AkkaRemoteServer {
  
	static void main(def args) {
		println("Application: Start a simple server console application for creating some Akka Actors and make them reachable from other (remote) processes\n")

		// setup phase
		println("setup: start at ${new Date()}.")

		// inline Akka configuration script, to enable publishing actors available in remote, and with some useful settings for a dev environment
		def akkaConfig = '''
		akka {
			loglevel = 'DEBUG'
			# log-config-on-start = on
			# daemonic = on # workaround to not keep it running here
			actor {
				provider = 'akka.remote.RemoteActorRefProvider'
			}
			remote {
				enabled-transports = ['akka.remote.netty.tcp']
				netty.tcp {
					hostname = '127.0.0.1'
					# Sever, listen on default Akka tcp port (2552)
					port = 2552
				}
				log-sent-messages = on
				log-received-messages = on
				log-remote-lifecycle-events = on
				log-frame-size-exceeding = on
				# log-buffer-size-exceeding = 50000
			}
		}
		'''
		println("Akka Config: $akkaConfig");

		def cl = this.class.classLoader
		println("using Groovy ClassLoader: $cl")
		println("using Akka version: ${ActorSystem.Version()}")

		Config config = // ConfigFactory.load()  // load from application.conf
			ConfigFactory.parseString(akkaConfig)  // parse the configuration inside the multi-line string

		// global actor system to start here
		final String remotableSystemName = "RemoteActorSystem"
		final ActorSystem system = // ActorSystem.create(remotableSystemName)
			// ActorSystem.create(remotableSystemName, config)
			// ActorSystem.create(remotableSystemName, config, cl)  // set Groovy classloader
			ActorSystem.create(remotableSystemName, config)  // do not set Groovy classloader when run from Gradle ...
		println("system: $system")
		// println("system configuration: ")
		// system.logConfiguration()  // log the real configuration of the system (could be different than akkaConfig) ...
		Props       props  = Props.create(GreetingActor.class)
		println("props: $props")
		sleep 500  // workaround, mainly for flushing console output ...

		// create instance for some actors
		ActorRef actor = system.actorOf(props, "greeting_actor")
		println("Get Actor Reference to GreetingActor: $actor")
		// TODO: start more actors here ...

		sleep 500  // workaround, mainly for flushing console output ...
		println("setup: end at ${new Date()}.")


		println("check: start")
		println("Actor System instance: $system")
		assert system != null
		// get a reference to our greeting actor
		println("props: $props")
		assert props != null
		actor = system.actorOf(props);
		println("Actor Reference instance is: $actor")
		assert actor != null
		// send some test messages to the actor
		actor.tell(new Greeting("Test Greeting"), null)
		assert actor != null
		actor.tell(new String("Test String"), null)
		assert actor != null
		actor.tell(new GenericMessage<String>("simple generic message with a String"), null)
		assert actor != null
		sleep 500  // workaround, mainly for flushing console output ...
		println("check: end at ${new Date()}.")


		println("check (remote): start");
		println("Actor System instance: $system")
		assert system != null
		// get a selection to our remote greeting actor
		final String remoteSystemName = "RemoteActorSystem"
		final String remoteBasePath = "akka.tcp://" + remoteSystemName + "@127.0.0.1:2552/user/"
		println("remote actor system base path: $remoteBasePath")
		final String remoteActorName = "greetingActor"  // "greeting_actor"
		ActorSelection selection = 
			system.actorSelection(remoteBasePath + remoteActorName)  // TODO: check if/how to do this but with context ...
		assert selection != null
		selection.tell("Test Remote", null)


		sleep(500);  // workaround, mainly for flushing console output ...
		System.out.println("check (remote): end at " + new java.util.Date() + ".")


		// system.tell("Start", null)  // TODO: temp ...
		println("Server ready ...")


		/*
		// TODO: make this application run, at least for one minute ...
		sleep 10000  // wait a little, to see if remote connections are accepted in the mean time ...
		// system.awaitTermination()  // TODO: check if needed ...
		 */

		/*
		// workaround, to keep this script running until user write a line of text
		sleep 500  // workaround, mainly for flushing console output ...
		println("\nHit ENTER to exit ...")
		println("(note that when running in Groovy Console, the input is being read from the text console in the background)")
		def quit = System.console()?.readLine()  // safer, when running in non-interactive mode (default) from Gradle ...

		system.shutdown()
		 */
		sleep 500  // workaround, mainly for flushing console output ...


// TODO: (later) make a little cleanup to move features into methods ...


		println("\nApplication: execution end at ${new Date()}.")
	}

}
