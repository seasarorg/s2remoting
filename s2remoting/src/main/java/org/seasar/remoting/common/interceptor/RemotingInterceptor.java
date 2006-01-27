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

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.framework.aop.interceptors.AbstractInterceptor;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.MethodUtil;
import org.seasar.remoting.common.connector.Connector;

/**
 * �����[�g�I�u�W�F�N�g�̃��\�b�h�Ăяo�����s�����߂̃C���^�[�Z�v�^�ł��B
 * <p>
 * ���̃C���^�[�Z�v�^��Java�C���^�t�F�[�X�܂��͒��ۃN���X�ɓK�p����A�Ăяo���ꂽ���\�b�h���^�[�Q�b�g�ɂ���Ď�������Ă��Ȃ��ꍇ(���ۃ��\�b�h)��
 * {@link Connector}�ɈϏ����邱�Ƃɂ�胊���[�g�I�u�W�F�N�g�̃��\�b�h�Ăяo�����s���܂��B
 * <p>
 * �C���^�[�Z�v�^�̓^�[�Q�b�g�̃R���|�[�l���g��`���疼�O( <code>&lt;component&gt;</code> �v�f��
 * <code>name</code> �����̒l)���擾���A���̖��O�������[�g�I�u�W�F�N�g�̖��O�Ƃ��� {@link Connector#invoke}
 * ���Ăяo���܂��B�R���|�[�l���g��`�ɖ��O����`����Ă��Ȃ��ꍇ�́A�R���|�[�l���g�̌^��( <code>&lt;component&gt;</code>
 * �v�f�� <code>class</code> �����̒l)����p�b�P�[�W�������������O�������[�g�I�u�W�F�N�g�̖��O�Ƃ��܂��B
 * �R���|�[�l���g�̌^������`����Ă��Ȃ��ꍇ�́A�^�[�Q�b�g�I�u�W�F�N�g�̃N���X������p�b�P�[�W�������������O�������[�g�I�u�W�F�N�g�̖��O�Ƃ��܂��B
 * �����v���p�e�B <code>remoteName</code>
 * (�I�v�V����)���ݒ肳��Ă���΁A���̒l����Ƀ����[�g�I�u�W�F�N�g�̖��O�Ƃ��Ďg�p����܂��B
 * 
 * @see Connector
 * @author koichik
 */
public class RemotingInterceptor extends AbstractInterceptor {

    // constants
    private static final long serialVersionUID = 1L;

    // instance fields
    protected Connector connector;
    protected String remoteName;

    /**
     * �����[�g�Ăяo�������s���� {@link Connector}��ݒ肵�܂��B���̃v���p�e�B�͕K�{�ł��B
     * 
     * @param connector
     *            �����[�g�Ăяo�������s���� {@link Connector}
     */
    public void setConnector(final Connector connector) {
        this.connector = connector;
    }

    /**
     * �����[�g�I�u�W�F�N�g�̖��O��ݒ肵�܂��B���̃v���p�e�B�̓I�v�V�����ł��B
     * �R���|�[�l���g��`����擾�ł��閼�O���g�����Ƃ��o���Ȃ��ꍇ�ɂ̂ݐݒ肵�Ă��������B
     * 
     * @param remoteName
     *            �����[�g�I�u�W�F�N�g�̖��O
     */
    public void setRemoteName(final String remoteName) {
        this.remoteName = remoteName;
    }

    /**
     * �^�[�Q�b�g�̃��\�b�h���N�����ꂽ���ɌĂяo����܂��B�N�����ꂽ���\�b�h�����ۃ��\�b�h�Ȃ� {@link Connector}�ɈϏ����܂��B
     * ��ۃ��\�b�h�Ȃ�^�[�Q�b�g�̃��\�b�h���Ăяo���܂��B
     * 
     * @param invocation
     *            ���\�b�h�̋N�����
     */
    public Object invoke(final MethodInvocation invocation) throws Throwable {
        final Method method = invocation.getMethod();
        if (MethodUtil.isAbstract(method)) {
            return connector.invoke(getRemoteName(invocation), method, invocation.getArguments());
        }
        return invocation.proceed();
    }

    /**
     * �����[�g�I�u�W�F�N�g�̖��O��Ԃ��܂��B�����[�g�I�u�W�F�N�g�̖��O�͎��̏��ŉ������܂��B
     * <ul>
     * <li>�v���p�e�B <code>remoteName</code> ���ݒ肳��Ă���΂��̒l�B</li>
     * <li>�R���|�[�l���g��`�ɖ��O���ݒ肳��Ă���΂��̒l�B</li>
     * <li>�R���|�[�l���g��`�Ɍ^���ݒ肳��Ă���΂��̖��O����p�b�P�[�W�����������l�B</li>
     * <li>�^�[�Q�b�g�I�u�W�F�N�g�̌^������p�b�P�[�W�����������l�B</li>
     * </ul>
     * 
     * @param invocation
     *            ���\�b�h�̋N�����
     * @return �����[�g�I�u�W�F�N�g�̖��O
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
}