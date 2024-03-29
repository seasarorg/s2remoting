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
package org.seasar.remoting.common.connector.impl;

import java.net.URL;

import org.seasar.remoting.common.connector.Connector;

/**
 * URLに基づいてリモートメソッド呼び出しを行うコネクタの抽象基底クラスです。
 * 
 * @author koichik
 */
public abstract class URLBasedConnector implements Connector {

    // instance fields
    protected URL baseURL;

    /**
     * ベースURLを返します。
     * 
     * @return ベースURL
     */
    public URL getBaseURL() {
        return baseURL;
    }

    /**
     * ベースURLを設定します。
     * 
     * @param baseURL
     *            ベースURLの文字列です
     */
    public void setBaseURL(final URL baseURL) {
        this.baseURL = baseURL;
    }
}
