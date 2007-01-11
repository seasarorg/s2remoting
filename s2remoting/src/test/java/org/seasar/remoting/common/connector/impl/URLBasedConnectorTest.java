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

import org.seasar.extension.unit.S2TestCase;

/**
 * @author koichik
 */
public class URLBasedConnectorTest extends S2TestCase {

    public URLBasedConnectorTest() {
    }

    public URLBasedConnectorTest(String name) {
        super(name);
    }

    public void setUp() {
        include("URLBasedConnectorTest.dicon");
    }

    public void test() throws Exception {
        URLBasedConnector connector = (URLBasedConnector) getComponent(TestConnector.class);
        assertEquals(new URL("http://localhost/path"), connector.getBaseURL());
    }

    public static class TestConnector extends URLBasedConnector {

        public Object invoke(String name, Method method, Object[] args) {
            return null;
        }
    }
}