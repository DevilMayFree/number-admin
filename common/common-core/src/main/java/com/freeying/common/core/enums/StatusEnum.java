package com.freeying.common.core.enums;

import com.freeying.common.core.utils.Assert;
import com.freeying.common.core.utils.EnumUtil;

import java.io.Serializable;
import java.util.function.Function;

/**
 * StatusEnum
 *
 * @author fx
 */
public enum StatusEnum implements Serializable {

    /**
     * 启用
     **/
    ENABLE(1, true, "启用"),
    /**
     * 禁用
     **/
    DISABLE(0, false, "禁用");

    private final Integer value;
    private final Boolean bool;
    private final String desc;

    private static final Function<Integer, StatusEnum> FUNC = EnumUtil.lookupMap(StatusEnum.class, StatusEnum::getValue);

    StatusEnum(int value, Boolean bool, String desc) {
        this.value = value;
        this.bool = bool;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public Boolean getBool() {
        return bool;
    }

    public String getDesc() {
        return desc;
    }

    /**
     * getEnumIfPrent or null
     *
     * @param value value
     * @return ElementEnum
     */
    public static StatusEnum getEnum(Integer value) {
        Assert.notNull(value, "StatusEnum value is null");
        return FUNC.apply(value);
    }

    /**
     * getEnumIfPrent or null
     *
     * @param bool bool
     * @return ElementEnum
     */
    public static StatusEnum getEnum(Boolean bool) {
        Assert.notNull(bool, "StatusEnum value is null");
        if (StatusEnum.ENABLE.getBool().equals(bool)) {
            return StatusEnum.ENABLE;
        }
        return StatusEnum.DISABLE;
    }

    /**
     * validValue
     *
     * @param value value
     * @return boolean
     */
    public static boolean isValidValue(Integer value) {
        return value != null && getEnum(value) != null;
    }

}
