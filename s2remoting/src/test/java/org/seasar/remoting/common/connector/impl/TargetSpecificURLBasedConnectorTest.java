/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.remoting.common.connector.impl;

import java.lang.reflect.Method;
import java.net.URL;

import junit.framework.TestCase;

/**
 * @author Koichi Kobayashi
 */
public class TargetSpecificURLBasedConnectorTest extends TestCase {

    public TargetSpecificURLBasedConnectorTest() {
    }

    public TargetSpecificURLBasedConnectorTest(String name) {
        super(name);
    }

    public void testGetTargetURL() throws Exception {
        TargetSpecificURLBasedConnector connector = new TargetSpecificURLBasedConnector() {

            protected Object invoke(URL url, Method method, Object[] params) {
                return null;
            }
        };

        // ends with /
        connector.setBaseURL(new URL("http://localhost/context/"));
        assertEquals(new URL("http://localhost/context/Foo"), connector.getTargetURL("Foo"));

        // ends without /
        connector.setBaseURL(new URL("http://localhost/context"));
        assertEquals(new URL("http://localhost/Bar"), connector.getTargetURL("Bar"));
    }

    public void testLRUMap() {
        TargetSpecificURLBasedConnector.LRUMap map = new TargetSpecificURLBasedConnector.LRUMap(1);
        assertEquals(0, map.size());

        map.put("1", "1");
        assertEquals(1, map.size());

        map.put("2", "2");
        assertEquals(1, map.size());

        map.setMaxSize(2);
        map.put("3", "3");
        assertEquals(2, map.size());

        map.put("4", "4");
        assertEquals(2, map.size());

        map.setMaxSize(0);
        map.put("5", "5");
        assertEquals(3, map.size());
    }
}