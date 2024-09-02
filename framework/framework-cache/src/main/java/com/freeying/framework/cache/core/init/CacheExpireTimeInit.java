package com.freeying.framework.cache.core.init;

import cn.hutool.core.map.MapUtil;
import com.freeying.framework.cache.annotation.AppCacheable;
import com.freeying.framework.cache.utils.CacheHelper;
import jakarta.annotation.Nonnull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

@Component
public class CacheExpireTimeInit implements SmartInitializingSingleton, BeanFactoryAware {

    private DefaultListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(@Nonnull BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    @Override
    public void afterSingletonsInstantiated() {
        Map<String, Object> beansWithAnnotation = beanFactory.getBeansWithAnnotation(Component.class);
        if (MapUtil.isNotEmpty(beansWithAnnotation)) {
            for (Object cacheBean : beansWithAnnotation.values()) {

                Class<?> clazz = cacheBean.getClass().getSuperclass();
                Method[] methods = ReflectionUtils.getDeclaredMethods(clazz);

                // 模块化后反射权限 排除特定的函数
                Arrays.stream(methods)
                        .filter(m -> !"finalize".equals(m.getName())
                                && !"wait0".equals(m.getName())
                                && !"clone".equals(m.getName())
                                && m.getDeclaringClass().equals(clazz))
                        .forEach(method -> {
                            ReflectionUtils.makeAccessible(method);
                            boolean cacheAnnotationPresent = method.isAnnotationPresent(AppCacheable.class);
                            if (cacheAnnotationPresent) {
                                AppCacheable appCacheable = method.getAnnotation(AppCacheable.class);
                                CacheHelper.initExpireTime(appCacheable);
                            }
                        });
            }
            CacheHelper.initializeCaches();
        }
    }

}
