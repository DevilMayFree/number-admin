package com.freeying.common.core.constant;

/**
 * CacheConstants
 *
 * @author fx
 */
public final class CacheConstants {

    /**
     * 字典缓存key
     */
    public static final String SYS_DICT_DATA = "sys:dict:data";

    public static final String SYS_DICT_DATA_KEY = SYS_DICT_DATA + ":";

    /**
     * 产品分类缓存
     */
    public static final String IOT_CATEGORY_DATA = "iot:category:data";

    public static final String IOT_CATEGORY_DATA_ALL = IOT_CATEGORY_DATA + ":*";

    /**
     * 资源缓存key
     */
    public static final String SYS_RESOURCE_KEY = "sys:resource:*";

    /**
     * 物模型缓存
     */
    public static final String TSL_KEY = "iot:things_model:tsl:";

    public static final String TSL_KEY_ALL = "iot:things_model:tsl:*";

    /**
     * 设备属性值Hset值,保留最新的属性值
     */
    public static final String DEVICE_PROPERTY_HSET_KEY = "iot:device:%s:%s:property_data";

    /**
     * 设备属性值set
     */
    public static final String DEVICE_PROPERTY_KEY = "iot:device:%s:%s:property_log:%s";

    /**
     * 设备事件值set
     */
    public static final String DEVICE_EVENT_KEY = "iot:device:%s:%s:event:";

    /**
     * 设备服务值set
     */
    public static final String DEVICE_SERVICE_KEY = "iot:device:%s:%s:service:";

    /**
     * 设备日志默认过期时间
     */
    public static final long DEVICE_LOG_EXPIRE_TIME = 604800L;

    private CacheConstants() {
    }
}
