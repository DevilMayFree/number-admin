package com.freeying.framework.data.generator;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;

/**
 * 自定义ID生成器
 *
 * @author fx
 */
public class CustomIdGenerator implements IdentifierGenerator {

    @Override
    public Number nextId(Object entity) {
        return IdentifierUtil.generateKey();
    }

}
