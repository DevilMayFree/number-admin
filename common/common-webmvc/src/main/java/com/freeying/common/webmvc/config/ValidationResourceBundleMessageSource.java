package com.freeying.common.webmvc.config;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import jakarta.annotation.Nonnull;
import java.io.IOException;
import java.util.Properties;

/**
 * ValidationResourceBundleMessageSource
 * <p>
 * 覆盖了类 ReloadableResourceBundleMessageSource 上的方法 refreshProperties 使其支持 classpath*
 * 用于加载子模块下的resource文件
 *
 * @author fx
 */
public class ValidationResourceBundleMessageSource extends ReloadableResourceBundleMessageSource {

    private static final String PROPERTIES_SUFFIX = ".properties";

    private final PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

    @Nonnull
    @Override
    protected PropertiesHolder refreshProperties(String filename, PropertiesHolder propHolder) {
        if (filename.startsWith(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX)) {
            return refreshClassPathProperties(filename, propHolder);
        } else {
            return super.refreshProperties(filename, propHolder);
        }
    }

    private PropertiesHolder refreshClassPathProperties(String filename, PropertiesHolder propHolder) {
        Properties properties = new Properties();
        long lastModified = -1;
        try {
            Resource[] resources = resolver.getResources(filename + PROPERTIES_SUFFIX);
            for (Resource resource : resources) {
                String sourcePath = resource.getURI().toString().replace(PROPERTIES_SUFFIX, "");
                PropertiesHolder holder = super.refreshProperties(sourcePath, propHolder);
                properties.putAll(holder.getProperties());
                if (lastModified < resource.lastModified()) {
                    lastModified = resource.lastModified();
                }
            }
        } catch (IOException ignored) {
            // ignore IOException
        }
        return new PropertiesHolder(properties, lastModified);
    }
}
