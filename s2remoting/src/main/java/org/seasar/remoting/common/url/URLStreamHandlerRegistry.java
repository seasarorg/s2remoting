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

import java.net.URL;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * <code>URLStreamHandler</code> �̃��W�X�g���ł��B <br>
 * ���̃��W�X�g���� <code>URLStreamHandlerFactory</code> �ł���A <code>URL</code>
 * �N���X�ɐݒ肳��܂��B {@link #createURLStreamHandler(String)}
 * ���Ăяo�����ƁA�o�^����Ă��� <code>URLStreamHandler</code> ��Ԃ��܂��B
 * 
 * @author koichik
 */
public class URLStreamHandlerRegistry implements URLStreamHandlerFactory {
    protected static final Map registry = Collections.synchronizedMap(new HashMap());

    static {
        URL.setURLStreamHandlerFactory(new URLStreamHandlerRegistry());
    }

    /**
     * �C���X�^���X���\�z���܂��B
     */
    private URLStreamHandlerRegistry() {
    }

    /**
     * �w�肳�ꂽ�v���g�R���̂��߂́A <code>URLStreamHandler</code> �̐V�����C���X�^���X���쐬���܂��B
     * 
     * @param protocol
     *            �v���g�R�� (<code>rmi</code> �Ȃ�)
     */
    public URLStreamHandler createURLStreamHandler(final String protocol) {
        return (URLStreamHandler) registry.get(protocol);
    }

    /**
     * �v���g�R���̂��߂̐V���� <code>URLStreamHandler</code> ��o�^���܂��B
     * 
     * @param protocol
     *            �v���g�R�� (<code>rmi</code> �Ȃ�
     * @param handler
     *            �v���g�R���̂��߂� <code>URLStreamHandler</code>
     */
    public static void registerHandler(final String protocol, final URLStreamHandler handler) {
        registry.put(protocol, handler);
    }
}