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

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.framework.aop.interceptors.AbstractInterceptor;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.MethodUtil;
import org.seasar.remoting.common.connector.Connector;

/**
 * リモートオブジェクトのメソッド呼び出しを行うためのインターセプタです。
 * <p>
 * このインターセプタはJavaインタフェースまたは抽象クラスに適用され、 呼び出されたメソッドがターゲットによって実装されていない場合(抽象メソッド)か、
 * <code>forceRemote</code>がtrueの場合に {@link Connector}に委譲することによりリモートオブジェクトのメソッド呼び出しを行います。
 * <p>
 * インターセプタはターゲットのコンポーネント定義から名前( <code>&lt;component&gt;</code> 要素の
 * <code>name</code> 属性の値)を取得し、その名前をリモートオブジェクトの名前として {@link Connector#invoke}
 * を呼び出します。コンポーネント定義に名前が定義されていない場合は、コンポーネントの型名( <code>&lt;component&gt;</code>
 * 要素の <code>class</code> 属性の値)からパッケージ名を除いた名前をリモートオブジェクトの名前とします。
 * コンポーネントの型名が定義されていない場合は、ターゲットオブジェクトのクラス名からパッケージ名を除いた名前をリモートオブジェクトの名前とします。
 * もしプロパティ <code>remoteName</code>
 * (オプション)が設定されていれば、その値が常にリモートオブジェクトの名前として使用されます。
 * 
 * @see Connector
 * @author koichik
 */
public class RemotingInterceptor extends AbstractInterceptor {

    // constants
    private static final long serialVersionUID = 1L;

    // annotations
    public static final String connector_BINDING = "bindingType=must";

    // instance fields
    protected Connector connector;

    protected String remoteName;

    protected boolean forceRemote = false;

    /**
     * リモート呼び出しを実行する {@link Connector}を設定します。このプロパティは必須です。
     * 
     * @param connector
     *            リモート呼び出しを実行する {@link Connector}
     */
    public void setConnector(final Connector connector) {
        this.connector = connector;
    }

    /**
     * リモートオブジェクトの名前を設定します。このプロパティはオプションです。
     * コンポーネント定義から取得できる名前を使うことが出来ない場合にのみ設定してください。
     * 
     * @param remoteName
     *            リモートオブジェクトの名前
     */
    public void setRemoteName(final String remoteName) {
        this.remoteName = remoteName;
    }

    /**
     * ターゲットのメソッドが起動された時に呼び出されます。 起動されたメソッドが抽象メソッドか、 <code>forceRemote</code>がtrueならば、
     * 処理を {@link Connector}に委譲します。 そうでない場合は、ターゲットの具象メソッドを呼び出します。
     * 
     * @param invocation
     *            メソッドの起動情報
     */
    public Object invoke(final MethodInvocation invocation) throws Throwable {
        final Method method = invocation.getMethod();
        if (MethodUtil.isAbstract(method) || forceRemote) {
            return connector.invoke(getRemoteName(invocation), method, invocation.getArguments());
        }
        return invocation.proceed();
    }

    /**
     * リモートオブジェクトの名前を返します。リモートオブジェクトの名前は次の順で解決します。
     * <ul>
     * <li>プロパティ <code>remoteName</code> が設定されていればその値。</li>
     * <li>コンポーネント定義に名前が設定されていればその値。</li>
     * <li>コンポーネント定義に型が設定されていればその名前からパッケージ名を除いた値。</li>
     * <li>ターゲットオブジェクトの型名からパッケージ名を除いた値。</li>
     * </ul>
     * 
     * @param invocation
     *            メソッドの起動情報
     * @return リモートオブジェクトの名前
     */
    protected String getRemoteName(final MethodInvocation invocation) {
        if (remoteName != null) {
            return remoteName;
        }

        final ComponentDef componentDef = getComponentDef(invocation);
        final String componentName = componentDef.getComponentName();
        if (componentName != null) {
            return componentName;
        }

        final Class componentClass = componentDef.getComponentClass();
        if (componentClass != null) {
            return ClassUtil.getShortClassName(componentClass);
        }

        return ClassUtil.getShortClassName(invocation.getThis().getClass());
    }

    /**
     * このオブジェクトが、強制的にリモート呼び出しを行うかどうかの設定を返します。
     * 
     * @return 強制的にリモート呼び出しを行うかどうかの指定
     */
    public boolean isForceRemote() {
        return forceRemote;
    }

    /**
     * 呼び出されるメソッドが抽象メソッドでない場合も、 リモート呼び出しを行うかどうかを設定します。
     * trueの場合は必ずリモート呼び出し、falseの場合は抽象メソッドの場合のみ、 リモート呼び出しを行うようになります。
     * デフォルトはfalseです。
     * 
     * @param forceRemote
     *            強制的にリモート呼び出しを行うかどうかの指定
     */
    public void setForceRemote(boolean forceRemote) {
        this.forceRemote = forceRemote;
    }
}
