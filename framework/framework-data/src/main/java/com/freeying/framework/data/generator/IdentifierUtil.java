package com.freeying.framework.data.generator;

import com.freeying.framework.data.snowflake.keygen.IpSectionKeyGenerator;

/**
 * IdentifierUtil
 *
 * @author fx
 */
public final class IdentifierUtil {
    private static final IpSectionKeyGenerator IP_SECTION_KEY_GENERATOR = new IpSectionKeyGenerator();

    private IdentifierUtil() {
    }

    public static Long genId() {
        return generateKey().longValue();
    }

    public static Number generateKey() {
        return IP_SECTION_KEY_GENERATOR.generateKey();
    }
}
