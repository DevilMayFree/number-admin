package com.freeying.framework.cache.core.model;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.MethodInvoker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * CachedInvocation
 * <p>标记了缓存注解的方法类信息,用于主动刷新缓存时调用原始方法加载数据</p>
 *
 * @author fx
 */
public final class CachedInvocation {

    private CacheMetaData metaData;
    private Object targetBean;
    private Method targetMethod;
    private Object[] arguments;


    public Object invoke()
            throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final MethodInvoker invoker = new MethodInvoker();
        invoker.setTargetObject(this.getTargetBean());
        invoker.setArguments(this.getArguments());
        invoker.setTargetMethod(this.getTargetMethod().getName());
        invoker.prepare();
        return invoker.invoke();
    }


    public CacheMetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(CacheMetaData metaData) {
        this.metaData = metaData;
    }

    public Object getTargetBean() {
        return targetBean;
    }

    public void setTargetBean(Object targetBean) {
        this.targetBean = targetBean;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public void setTargetMethod(Method targetMethod) {
        this.targetMethod = targetMethod;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("metaData", metaData)
                .append("targetBean", targetBean)
                .append("targetMethod", targetMethod)
                .append("arguments", arguments)
                .toString();
    }
}

