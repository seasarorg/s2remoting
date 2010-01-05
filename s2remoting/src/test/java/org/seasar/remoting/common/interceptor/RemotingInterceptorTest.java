/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
package org.seasar.remoting.common.interceptor;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.aop.interceptors.MockInterceptor;

/**
 * @author koichik
 */
public class RemotingInterceptorTest extends S2TestCase {

    private MockInterceptor mockInterceptor;

    private RemotingInterceptor remotingInterceptor;

    public RemotingInterceptorTest() {
    }

    public RemotingInterceptorTest(String name) {
        super(name);
    }

    public void setUp() {
        include("RemotingInterceptorTest.dicon");
    }

    public void testInvoke() {
        // call concrete method
        Hoge hoge = (Hoge) getComponent(Hoge.class);
        hoge.foo();
        assertFalse(mockInterceptor.isInvoked("invoke"));

        // call abstract method
        hoge.bar();
        assertTrue(mockInterceptor.isInvoked("invoke"));
    }

    public void testInvoke_forceRemote() {
        remotingInterceptor.setForceRemote(true);

        Hoge hoge = (Hoge) getComponent(Hoge.class);
        hoge.foo();
        assertTrue(mockInterceptor.isInvoked("invoke"));
    }

    public void testGetTargetName() {
        // from name attribute of component
        Hoge hoge = (Hoge) getComponent(Hoge.class);
        hoge.bar();
        Object[] args = mockInterceptor.getArgs("invoke");
        assertEquals("hoge", args[0]);

        // from class attribute of component
        Foo foo = (Foo) getComponent(Foo.class);
        foo.foo();
        args = mockInterceptor.getArgs("invoke");
        assertEquals("RemotingInterceptorTest$Foo", args[0]);

        // from remoteName property
        Bar bar = (Bar) getComponent(Bar.class);
        bar.bar();
        args = mockInterceptor.getArgs("invoke");
        assertEquals("bar", args[0]);
    }

    public interface Hoge {

        void foo();

        void bar();
    }

    public static abstract class HogeImpl implements Hoge {

        public void foo() {
        }
    }

    public interface Foo {

        void foo();
    }

    public interface Bar {

        void bar();
    }
}