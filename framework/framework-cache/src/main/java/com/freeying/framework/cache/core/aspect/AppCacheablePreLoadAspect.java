package com.freeying.framework.cache.core.aspect;

import com.freeying.common.core.utils.SpringContextHolder;
import com.freeying.framework.cache.annotation.AppCacheable;
import com.freeying.framework.cache.constants.CacheConstant;
import com.freeying.framework.cache.core.model.CacheMetaData;
import com.freeying.framework.cache.core.model.CachedInvocation;
import com.freeying.framework.cache.utils.CacheHelper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;

/**
 * AppCacheablePreLoadAspect
 * <p>拆成2个切面是为让AppCacheableExpireAspect比AppCacheablePreLoadAspect先执行</p>
 *
 * @author fx
 */
@Component
@Aspect
@Order(2)
public class AppCacheablePreLoadAspect {

    private final ApplicationContext applicationContext;

    public AppCacheablePreLoadAspect(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Around(value = "@annotation(appCacheable)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint, AppCacheable appCacheable) throws Throwable {
        buildCachedInvocationAndPushlish(proceedingJoinPoint, appCacheable);
        return proceedingJoinPoint.proceed();
    }

    private void buildCachedInvocationAndPushlish(ProceedingJoinPoint proceedingJoinPoint, AppCacheable appCacheable) {
        Method method = this.getSpecificmethod(proceedingJoinPoint);
        String[] cacheNames = CacheHelper.getCacheNames(appCacheable);
        Object targetBean = proceedingJoinPoint.getTarget();
        Object[] arguments = proceedingJoinPoint.getArgs();
        KeyGenerator keyGenerator = SpringContextHolder.getBean(CacheConstant.CUSTOM_CACHE_KEY_GENERATOR, KeyGenerator.class);
        Object key = keyGenerator.generate(targetBean, method, arguments);

        CacheMetaData metaData = new CacheMetaData();
        metaData.setCacheNames(cacheNames);
        metaData.setKey(key);
        metaData.setExpiredTimeSecond(appCacheable.expiredTimeSecond());
        metaData.setPreLoadTimeSecond(appCacheable.preLoadTimeSecond());

        CachedInvocation invocation = new CachedInvocation();
        invocation.setMetaData(metaData);
        invocation.setArguments(arguments);
        invocation.setTargetBean(targetBean);
        invocation.setTargetMethod(method);

        applicationContext.publishEvent(invocation);
    }

    @SuppressWarnings("squid:S2583")
    private Method getSpecificmethod(ProceedingJoinPoint pjp) {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        // The method may be on an interface, but we need attributes from the
        // target class. If the target class is null, the method will be
        // unchanged.
        Class<?> targetClass = AopProxyUtils.ultimateTargetClass(pjp.getTarget());
        if (targetClass == null && pjp.getTarget() != null) {
            targetClass = pjp.getTarget().getClass();
        }
        Method specificMethod = ClassUtils.getMostSpecificMethod(method, targetClass);
        // If we are dealing with method with generic parameters, find the
        // original method.
        specificMethod = BridgeMethodResolver.findBridgedMethod(specificMethod);
        return specificMethod;
    }


}
