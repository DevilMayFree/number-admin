package com.freeying.framework.data.snowflake;

import com.freeying.framework.data.snowflake.keygen.IpSectionKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * IdGeneratorConfig
 *
 * @author fx
 */
@Configuration
public class IdGeneratorConfig {

    @Bean
    public IpSectionKeyGenerator ipSectionKeyGenerator() {
        return new IpSectionKeyGenerator();
    }

}
