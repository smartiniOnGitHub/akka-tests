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

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * Abstract base actor class, to use (when desired) as base class for all other actors here.
 */
public abstract class BaseActor extends UntypedActor
{
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object message) throws Exception
    {
		// do nothing special here, override in subclasses and maybe keep it as last process options ...

		if (message != null) {
			String messageClassName = message.getClass().getName();
			log.warning("Unknown message type " + messageClassName + ", contents: \"" + message.toString() + "\"");
		}
		else
			log.warning("Unknown message type, message is null");

		unhandled(message);
    }
}
