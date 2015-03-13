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

// import akka.testkit.JavaTestKit;  // only for tests

import scala.concurrent.duration.Duration;

/**
 * Greeting actor, as a sample.
 * <br/>
 * This code is derived from Akka Samples.
 */
public class GreetingActor extends UntypedActor
{
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

	/** Default constructor */
	public GreetingActor() {
	}

    @Override
    public void onReceive(Object message) throws Exception
    {
        String messageClassName = message.getClass().getName();

        if (message == null)
        {
            log.warning(messageClassName + ": null message");
            unhandled(message);
        }
        else if (message instanceof Greeting)
        {
			log.info(messageClassName + ": Hello \"" + ((Greeting) message).getWho() + "\"");
			getSender().tell("Hello \"" + ((Greeting) message).getWho() + "\"", getSelf());  // reply to the sender
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
