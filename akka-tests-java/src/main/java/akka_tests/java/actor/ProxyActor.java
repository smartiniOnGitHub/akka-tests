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

	// referencing another actor
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
		// log.debug("preStart ...");
		// connect to remote actor
// TODO: using getContext().actorSelection() ...
	}

	// @Override
	// public void postRestart(Throwable reason) {
	// }

	@Override
	public void postStop() {
		// log.debug("postStop ...");
		// cleanup
	}


    @Override
    public void onReceive(Object message) throws Exception
    {
        String messageClassName = message.getClass().getName();

		// forward/delegate all to otherActor ...
// TODO: ...
        if (message == null)
        {
			log.warning(messageClassName + ": null message");
			unhandled(message);
        }
        else if (message instanceof Stop)
        {
			log.info(messageClassName + ": " + "Stop this actor now ...");
			// Stops this actor and all its supervised children
			getContext().stop(getSelf());
        }
        else if (message instanceof Shutdown)
        {
            log.info(messageClassName + ": " + "Shutdown this akka system now ...");
            // Shutdown the entire akka system
            getContext().system().shutdown();
        }
        else if (message instanceof Wait)
        {
            long sleep = ((Wait) message).getWaitTime();
            log.info(messageClassName + ": " + "Waiting for " + sleep + " milliseconds now ...");
            // Sleep this actor for the given time
            long startSleep = System.currentTimeMillis();
            // sleep this actor
            // note that this is not the right way, but should be ok in this small test ...
            // because Thread.sleep breaks actors management as it will monopolize all threads of the used executor
            Thread.sleep(sleep);
            // note that probably instead it's needed something like this
            // getContext.system().getScheduler().scheduleOnce(sleep, sender, "Done")
            long stopSleep = System.currentTimeMillis();
            log.info("Wait: " + "End Waiting, after " + (stopSleep - startSleep) + " milliseconds.");
         } else if (message instanceof ActorRef) {
            log.info(messageClassName + ": Message from an ActorRef, now reply to it ...");
			otherActor = (ActorRef) message;
			getSender().tell("done", getSelf());
		} 
		else if (message instanceof String)
        {
			log.info(messageClassName + ": \"" + message.toString() + "\"");
			getSender().tell(message, getSelf());  // reply to the sender
		}
        else
        {
            if (message != null)
				log.warning("Unknown message type " + messageClassName + ", contents: \"" + message.toString() + "\"");
			else
				log.warning("Unknown message type " + messageClassName + ", message is null");

            unhandled(message);
        }
		/*
// TODO: enable after set base class ...        
		else {
			super.onReceive(message);
		}
		 */
    }
}
