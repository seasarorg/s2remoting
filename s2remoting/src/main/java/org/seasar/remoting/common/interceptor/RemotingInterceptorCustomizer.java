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
package org.seasar.remoting.common.interceptor;

import org.seasar.framework.container.ComponentCustomizer;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.PropertyDef;
import org.seasar.framework.container.impl.PropertyDefImpl;
import org.seasar.framework.container.ognl.OgnlExpression;
import org.seasar.remoting.common.connector.Connector;

/**
 * SMART deployにおいて{@link RemotingInterceptor}のコンポーネント定義を
 * カスタマイズするためのコンポーネントカスタマイザです。
 * 
 * @author koichik
 */
public class RemotingInterceptorCustomizer implements ComponentCustomizer {

    // constants
    protected static final String REMOTING_INTERCEPTOR_CLASS_NAME = "org.seasar.remoting.common.interceptor.RemotingInterceptor";

    // instance fields
    protected String connectorName;

    /**
     * リモート呼び出しに使用する{@link Connector}のコンポーネント名を設定します。
     * 
     * @param connectorName
     *            リモート呼び出しに使用する{@link Connector}のコンポーネント名
     */
    public void setConnectorName(final String connectorName) {
        this.connectorName = connectorName;
    }

    /**
     * コンポーネント定義をカスタマイズします。
     * <p>
     * {@link RemotingInterceptor}のコンポーネント定義に{@link Connector}をインジェクションするための
     * 設定をプロパティ定義を追加します．
     * </p>
     */
    public void customize(final ComponentDef componentDef) {
        if (componentDef.getComponentClass().getName().equals(REMOTING_INTERCEPTOR_CLASS_NAME)) {
            final PropertyDef propertyDef = new PropertyDefImpl("connector");
            propertyDef.setExpression(new OgnlExpression(connectorName));
            componentDef.addPropertyDef(propertyDef);
        }
    }

}
