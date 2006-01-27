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
import java.net.URLConnection;
import java.net.URLStreamHandler;

/**
 * �f�t�H���g�̃|�[�g�ԍ��������A�I�[�v�����邱�Ƃ̏o���Ȃ� <code>URL</code> �̂��߂�
 * <code>URLStreamHandler</code> �ł��B
 * 
 * @author koichik
 */
public class UnopenableURLStreamHandler extends URLStreamHandler {

    // instance fields
    protected final int defaultPort;

    /**
     * �w�肳�ꂽ�|�[�g�ԍ����f�t�H���g�Ƃ��Ď��V�����C���X�^���X���\�z���܂��B
     * 
     * @param defaultPort
     *            ���̃v���g�R���̃f�t�H���g�̃|�[�g�ԍ�
     */
    public UnopenableURLStreamHandler(final int defaultPort) {
        this.defaultPort = defaultPort;
    }

    /**
     * ���̑���̓T�|�[�g����܂���B
     * 
     * @throws UnsupportedOperationException
     *             ��ɃX���[����܂�
     */
    protected URLConnection openConnection(final URL url) {
        throw new UnsupportedOperationException();
    }

    /**
     * <code>URL</code> �����t�B�[���h�l���A�w�肳�ꂽ�l�ɐݒ肵�܂��B <br>
     * �|�[�g�ԍ����w�肳��Ă��Ȃ��ꍇ�́A�R���X�g���N�^�Ŏw�肳�ꂽ�f�t�H���g�̃|�[�g�ԍ���ݒ肵�܂��B
     * 
     * @param url
     *            �C������ <code>URL</code>
     * @param protocol
     *            �v���g�R����
     * @param host
     *            <code>URL</code> �̃����[�g�z�X�g�l
     * @param port
     *            �����[�g�}�V����̃|�[�g
     * @param authority
     *            <code>URL</code> �̌�������
     * @param userInfo
     *            <code>URL</code> �̃��[�U��񕔕�
     * @param path
     *            <code>URL</code> �̃p�X�R���|�[�l���g
     * @param query
     *            <code>URL</code> �̃N�G���[����
     * @param ref
     *            �Q��
     */
    protected void setURL(final URL url, final String protocol, final String host, int port,
            final String authority, final String userInfo, final String path, final String query,
            final String ref) {
        if (port == -1) {
            port = defaultPort;
        }
        super.setURL(url, protocol, host, port, authority, userInfo, path, query, ref);
    }
}