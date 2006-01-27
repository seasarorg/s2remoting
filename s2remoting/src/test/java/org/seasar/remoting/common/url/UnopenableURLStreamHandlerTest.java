/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
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
package org.seasar.remoting.common.url;

import java.net.MalformedURLException;
import java.net.URL;

import junit.framework.TestCase;

/**
 * @author koichik
 */
public class UnopenableURLStreamHandlerTest extends TestCase {

    public UnopenableURLStreamHandlerTest() {
    }

    public UnopenableURLStreamHandlerTest(String name) {
        super(name);
    }

    public void test() throws MalformedURLException {
        URLStreamHandlerRegistry.registerHandler("rmi", new UnopenableURLStreamHandler(1099));

        URL url = new URL("rmi://localhost/test");
        assertEquals("rmi", url.getProtocol());
        assertEquals(1099, url.getPort());
        assertEquals("localhost", url.getHost());
        assertEquals("/test", url.getPath());

        new URL("http://host/test");
        try {
            new URL("foo://host/hoge");
            fail();
        }
        catch (MalformedURLException expected) {
        }
    }
}
