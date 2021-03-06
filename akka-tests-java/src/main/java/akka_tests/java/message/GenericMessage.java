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
package akka_tests.java.message;

/**
 * GenericMessage.
 * <br/>
 * Used as kind of message to send to actors.
 */
public class GenericMessage<T> extends BaseMessage
{
	private static final long serialVersionUID = 1L;

    // read only property, with public getter generated by Groovy, but not setter
    final T message;

    public GenericMessage(T message)
    {
        this.message = message;
    }

	public T getMessage() {
		return message;
	}
}
