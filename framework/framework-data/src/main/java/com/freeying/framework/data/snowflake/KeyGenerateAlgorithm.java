package com.freeying.framework.data.snowflake;

/**
 * KeyGenerateAlgorithm
 *
 * @author fx
 */
public interface KeyGenerateAlgorithm {

    /**
     * id_generator
     *
     * @return Number
     */
    Number generateKey();
}