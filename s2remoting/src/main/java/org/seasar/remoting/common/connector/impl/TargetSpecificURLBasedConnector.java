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
package org.seasar.remoting.common.connector.impl;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * �^�[�Q�b�g�ƂȂ�I�u�W�F�N�g���ƂɌʂ�URL���g�p���ă����[�g���\�b�h�Ăяo�����s���R�l�N�^�̒��ۊ��N���X�ł��B
 * <p>
 * ���̃R�l�N�^�̓����[�g�I�u�W�F�N�g�̖��O���X�[�p�[�N���X�̃v���p�e�B�ɐݒ肳�ꂽ�x�[�XURL����̑���URL�Ƃ��ă^�[�Q�b�g�ƂȂ郊���[�g�I�u�W�F�N�g��URL���������܂��B
 * ���̂��߁C�x�[�XURL���X���b�V��( <code>/</code> )�ŏI�����Ă��Ȃ��ꍇ�A�\�����Ȃ����ʂɂȂ邩������Ȃ����Ƃɒ��ӂ��Ă��������B
 * <p>
 * ��������܂��B <br>
 * �x�[�XURL�����̂悤�ɐݒ肳��Ă���Ƃ��܂��B
 * 
 * <pre>
 *    http://host/context/services/
 * </pre>
 * 
 * �����[�g�I�u�W�F�N�g�����̖��O�ł���Ƃ��܂��B
 * 
 * <pre>
 * Foo
 * </pre>
 * 
 * �����[�g�I�u�W�F�N�g��URL�͎��̂悤�ɂȂ�܂��B
 * 
 * <pre>
 *    http://host/context/services/Foo
 * </pre>
 * 
 * �x�[�XURL�����̂悤�ɃX���b�V��( <code>/</code> )�ŏI�����Ă��Ȃ��ꍇ�͌��ʂ��قȂ�܂��B
 * 
 * <pre>
 *    http://host/context/services
 * </pre>
 * 
 * �����[�g�I�u�W�F�N�g�����̖��O�ł���Ƃ��܂��B
 * 
 * <pre>
 * Foo
 * </pre>
 * 
 * �����[�g�I�u�W�F�N�g��URL�͎��̂悤�ɂȂ�܂��B
 * 
 * <pre>
 *    http://host/context/Foo
 * </pre>
 * 
 * @author koichik
 */
public abstract class TargetSpecificURLBasedConnector extends URLBasedConnector {

    // constants
    /**
     * �����[�g�I�u�W�F�N�g��URL���L���b�V���������̃f�t�H���g�l
     */
    protected static final int DEFAULT_MAX_CACHED_URLS = 10;

    // instance fields
    protected LRUMap cachedURLs = new LRUMap(DEFAULT_MAX_CACHED_URLS);

    /**
     * �����[�g�I�u�W�F�N�g��URL���L���b�V����������ݒ肵�܂��B
     * 
     * @param maxCachedURLs
     *            �����[�g�I�u�W�F�N�g��URL���L���b�V���������ł�
     */
    public synchronized void setMaxCachedURLs(final int maxCachedURLs) {
        cachedURLs.setMaxSize(maxCachedURLs);
    }

    /**
     * �^�[�Q�b�g�ƂȂ郊���[�g�I�u�W�F�N�g��URL���������A�T�u�N���X�ŗL�̕��@�Ń��\�b�h�Ăяo�������s���܂��B
     * 
     * @param remoteName
     *            �����[�g�I�u�W�F�N�g�̖��O
     * @param method
     *            �Ăяo�����\�b�h
     * @param args
     *            �����[�g�I�u�W�F�N�g�̃��\�b�h�Ăяo���ɓn���������l���i�[����I�u�W�F�N�g�z��
     * @return �����[�g�I�u�W�F�N�g�ɑ΂��郁�\�b�h�Ăяo������̖߂�l
     * @throws Throwable
     *             �����[�g�I�u�W�F�N�g�ɑ΂��郁�\�b�h�Ăяo������X���[���ꂽ��O�ł�
     */
    public Object invoke(final String remoteName, final Method method, final Object[] args)
            throws Throwable {
        return invoke(getTargetURL(remoteName), method, args);
    }

    /**
     * �^�[�Q�b�g�ƂȂ郊���[�g�I�u�W�F�N�g��URL��Ԃ��܂��B
     * �����[�g�I�u�W�F�N�g��URL�́A�����[�g�I�u�W�F�N�g�̖��O���x�[�XURL����̑���URL�Ƃ��ĉ������܂��B
     * 
     * @param remoteName
     *            �^�[�Q�b�g�ƂȂ郊���[�g�I�u�W�F�N�g�̖��O
     * @return �^�[�Q�b�g�ƂȂ郊���[�g�I�u�W�F�N�g��URL
     * @throws MalformedURLException
     *             �x�[�XURL�ƃ����[�g�I�u�W�F�N�g�̖��O����URL���쐬�ł��Ȃ������ꍇ�ɃX���[����܂�
     */
    protected synchronized URL getTargetURL(final String remoteName) throws MalformedURLException {
        URL targetURL = (URL) cachedURLs.get(remoteName);
        if (targetURL == null) {
            targetURL = new URL(baseURL, remoteName);
            cachedURLs.put(remoteName, targetURL);
        }
        return targetURL;
    }

    /**
     * �T�u�N���X�ŗL�̕��@�Ń����[�g���\�b�h�̌Ăяo�������s���A���̌��ʂ�Ԃ��܂��B
     * 
     * @param targetURL
     *            �^�[�Q�b�g�ƂȂ郊���[�g�I�u�W�F�N�g��URL
     * @param method
     *            �Ăяo�����\�b�h
     * @param args
     *            �����[�g�I�u�W�F�N�g�̃��\�b�h�Ăяo���ɓn���������l���i�[����I�u�W�F�N�g�z��
     * @return �����[�g�I�u�W�F�N�g�ɑ΂��郁�\�b�h�Ăяo������̖߂�l
     * @throws Throwable
     *             �����[�g�I�u�W�F�N�g�ɑ΂��郁�\�b�h�Ăяo������X���[���ꂽ��O�ł�
     */
    protected abstract Object invoke(URL targetURL, Method method, Object[] args) throws Throwable;

    /**
     * LRU�}�b�v <br>
     * �G���g�����ɏ��������A����𒴂��ăG���g�����ǉ����ꂽ�ꍇ�ɂ͂����Ƃ��g�p����Ă��Ȃ��G���g������菜�����}�b�v�̎����ł��B
     * �G���g�����̏���͐������₷���Ƃ��o���܂����A���炵�Ă����̐��܂ŃG���g������菜����邱�Ƃ͂���܂���B ���̃}�b�v�͓�������܂���B
     * 
     * @author koichik
     */
    protected static class LRUMap extends LinkedHashMap {

        private static final long serialVersionUID = 1L;

        /** �f�t�H���g�����e�� */
        protected static final int DEFAULT_INITIAL_CAPACITY = 16;

        /** �f�t�H���g���׌W�� */
        protected static final float DEFAULT_LOAD_FACTOR = 0.75f;

        protected int maxSize;

        /**
         * �f�t�H���g�̏����e�ʂƕ��׌W���Ŏw�肳�ꂽ�G���g����������Ƃ���C���X�^���X���\�z���܂��B
         * 
         * @param maxSize
         *            �G���g�����̍ő吔
         */
        public LRUMap(final int maxSize) {
            this(maxSize, DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
        }

        /**
         * �w�肳�ꂽ�����e�ʂƕ��׌W���A�G���g�����̏�������C���X�^���X���\�z���܂��B
         * 
         * @param maxSize
         *            �G���g�����̍ő吔
         * @param initialCapacity
         *            �����e��
         * @param loadFactor
         *            ���׌W��
         */
        public LRUMap(final int maxSize, final int initialCapacity, final float loadFactor) {
            super(initialCapacity, loadFactor, true);
            this.maxSize = maxSize;
        }

        /**
         * �G���g�����̍ő�l��ݒ肵�܂��B
         * 
         * @param maxSize
         *            �G���g�����̍ő吔
         */
        public void setMaxSize(final int maxSize) {
            this.maxSize = maxSize;
        }

        /**
         * �}�b�v�̃G���g�������ő吔�𒴂��Ă���ꍇ <code>true</code> ��Ԃ��܂��B
         * ���̌��ʁA�ł��O�Ƀ}�b�v�ɑ}�����ꂽ�G���g�����}�b�v����폜����܂��B
         * 
         * @param eldest
         *            �����Ƃ��O�Ƀ}�b�v�ɑ}�����ꂽ�G���g��
         * @return �}�b�v�̃G���g�������ő吔�𒴂��Ă���ꍇ <code>true</code>
         */
        protected boolean removeEldestEntry(final Map.Entry eldest) {
            return maxSize > 0 && size() > maxSize;
        }
    }
}