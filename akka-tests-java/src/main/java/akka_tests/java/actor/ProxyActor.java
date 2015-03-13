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
package akka_tests.java.actor;

import akka_tests.java.message.Greeting;
import akka_tests.java.message.Shutdown;
import akka_tests.java.message.Stop;
import akka_tests.java.message.Wait;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import akka.util.Timeout;
// import akka.testkit.*;  // only for tests

// import scala.concurrent.duration.Duration;

// import java.util.concurrent.TimeUnit;


/**
 * Proxy actor, as a sample for using remote actors.
 * <br/>
 */
public class ProxyActor extends UntypedActor
{
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

	// referencing another (destination) actor, and delegate all to it
	private ActorRef otherActor;  // = null;  // getContext().actorOf(new Props(OtherActor.class), "other_actor");
	private String   actorPath;   // = null;
	private Timeout  timeout;


	/** Default constructor */
	public ProxyActor() {
		this(ActorRef.noSender(), null, null);
	}

	// add a new version of the constructor with a reference to the otherActor, so now I must define even the standard (no arg) constructor ...
	public ProxyActor(ActorRef otherActor) {
		this(otherActor, null, null);
	}

	// add a new version of the constructor with a reference to the otherActor and its path, so now I must define even the standard (no arg) constructor ...
	public ProxyActor(ActorRef otherActor, String actorPath, Timeout timeout) {
		this.otherActor = otherActor;
		this.actorPath  = actorPath;
		this.timeout    = timeout;
	}

	// add a new version of the constructor with the path the other actor, so now I must define even the standard (no arg) constructor ...
	public ProxyActor(String actorPath) {
		this(ActorRef.noSender(), actorPath, null);
	}

	// add a new version of the constructor with the path the other actor and the timeout to use, so now I must define even the standard (no arg) constructor ...
	public ProxyActor(String actorPath, Timeout timeout) {
		this(ActorRef.noSender(), actorPath, timeout);
	}


	@Override
	public void preStart() {
		log.info("preStart ...");  // later use log.debug instead ...
		if (otherActor == null)
			otherActor = ActorRef.noSender();

		// connect to remote actor
// TODO: using getContext().actorSelection() ...
		log.info("preStart: otherActor is " + otherActor);  // later use log.debug instead ...
	}

	// @Override
	// public void postRestart(Throwable reason) {
	// 	log.debug("postRestart ...");
	// }

	@Override
	public void postStop() {
		log.info("postStop ...");  // later use log.debug instead ...
		// cleanup
	}


    @Override
    public void onReceive(Object message) throws Exception
    {
        String messageClassName = message.getClass().getName();

		// forward/delegate all to otherActor ...
		if (otherActor != null) {
			log.info(messageClassName + ": forward to actor " + otherActor);
			otherActor.tell(message, getSelf());
			// getSender().tell("done", getSelf());
		}
		else {
			log.info(messageClassName + ", destination actor is null: skipping to unhandled");
			unhandled(message);
		}
    }
}
