package com.freeying.common.core.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.freeying.common.core.jackson.JacksonJavaTimeModule;

/**
 * JacksonHolder
 *
 * @author fx
 */
public final class JacksonHolder {
    private static final ObjectMapper INSTANCE = new ObjectMapper();

    private JacksonHolder() {
    }

    public static ObjectMapper getInstance() {
        INSTANCE.registerModules(new JavaTimeModule(), new JacksonJavaTimeModule());
        return JacksonHolder.INSTANCE;
    }

    public static TypeFactory getTypeFactory() {
        return getInstance().getTypeFactory();
    }
}
