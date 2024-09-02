package com.freeying.framework.data.snowflake.keygen;


import com.freeying.framework.data.snowflake.SnowflakeKeyGenerateAlgorithm;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

/**
 * 根据机器名最后的数字编号获取工作进程Id.如果线上机器命名有统一规范,建议使用此种方式.
 * 列如机器的HostName为:dangdang-db-sharding-dev-01(公司名-部门名-服务名-环境名-编号)
 * ,会截取HostName最后的编号01作为workerId.
 *
 * @author DonneyYoung
 **/
public final class HostNameKeyGenerator extends SnowflakeKeyGenerateAlgorithm {

    private static final String WORKER_ID_KEY = "worker-id";

    private final SnowflakeKeyGenerateAlgorithm defaultKeyGenerator = new SnowflakeKeyGenerateAlgorithm();

    public HostNameKeyGenerator(){
        InetAddress address;
        long workerId;
        try {
            address = InetAddress.getLocalHost();
        } catch (final UnknownHostException e) {
            throw new IllegalStateException("Cannot get LocalHost InetAddress, please check your network!");
        }
        String hostName = address.getHostName();
        try {
            workerId = Long.parseLong(hostName.replace(hostName.replaceAll("\\d+$", ""), ""));
        } catch (final NumberFormatException e) {
            throw new IllegalArgumentException(String.format("Wrong hostname:%s, hostname must be end with number!", hostName));
        }
        Properties props = new Properties();
        props.put(WORKER_ID_KEY,workerId);
        defaultKeyGenerator.setProps(props);
        defaultKeyGenerator.init();
    }

    @Override
    public synchronized Number generateKey() {
        return defaultKeyGenerator.generateKey();
    }
}
