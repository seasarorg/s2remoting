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
package org.seasar.remoting.common.creator;

import org.seasar.framework.container.ComponentCreator;
import org.seasar.framework.container.ComponentCustomizer;
import org.seasar.framework.container.creator.ComponentCreatorImpl;
import org.seasar.framework.convention.NamingConvention;
import org.seasar.remoting.common.connector.Connector;

/**
 * SMART deployにおいて{@link Connector}のコンポーネント定義を作成する{@link ComponentCreator}の実装クラスです。
 * 
 * @author koichik
 */
public class ConnectorCreator extends ComponentCreatorImpl {

    /**
     * インスタンスを構築します。
     * 
     * @param namingConvention
     *            ネーミングコンベンション
     */
    public ConnectorCreator(final NamingConvention namingConvention) {
        super(namingConvention);
        setNameSuffix(namingConvention.getConnectorSuffix());
        setEnableInterface(false);
        setEnableAbstract(false);
    }

    /**
     * コンポーネントカスタマイザを返します。
     * 
     * @return コンポーネントカスタマイザ
     */
    public ComponentCustomizer getConnectorCustomizer() {
        return getCustomizer();
    }

    /**
     * コンポーネントカスタマイザを設定します。
     * 
     * @param customizer
     *            コンポーネントカスタマイザ
     */
    public void setConnectorCustomizer(final ComponentCustomizer customizer) {
        setCustomizer(customizer);
    }

}