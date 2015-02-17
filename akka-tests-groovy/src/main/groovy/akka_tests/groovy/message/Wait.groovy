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
package akka_tests.groovy.message

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

// import org.junit.*

/**
 * Waiting message, as a sample.
 * <br/>
 * Used as kind of message to send to actors.
 */
@EqualsAndHashCode
@ToString
class Wait implements Serializable
{
    final long msec

    Wait(long msec)
    {
        // Assert.true(msec >= 0, "Waiting time must be 0 or positive.")
        assert msec >= 0, "Waiting time must be 0 or positive."
        this.msec = msec
    }
}