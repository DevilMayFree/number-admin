package com.freeying.common.core.enums;

import com.freeying.common.core.utils.Assert;
import com.freeying.common.core.utils.EnumUtil;

import java.io.Serializable;
import java.util.function.Function;

/**
 * TrueFalseEnum
 *
 * @author fx
 */
public enum TrueFalseEnum implements Serializable {

    /**
     * true
     **/
    TRUE(1, "true"),
    /**
     * false
     **/
    FALSE(0, "false");

    private final Integer value;
    private final String desc;

    private static final Function<Integer, TrueFalseEnum> FUNC = EnumUtil.lookupMap(TrueFalseEnum.class, TrueFalseEnum::getValue);

    TrueFalseEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
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
    public static TrueFalseEnum getEnum(Integer value) {
        Assert.notNull(value, "StatusEnum value is null");
        return FUNC.apply(value);
    }

    /**
     * getEnumIfPrent or null
     *
     * @param bool bool
     * @return ElementEnum
     */
    public static TrueFalseEnum getEnum(String bool) {
        Assert.notNull(bool, "StatusEnum value is null");
        if (TrueFalseEnum.TRUE.getDesc().equalsIgnoreCase(bool)) {
            return TrueFalseEnum.TRUE;
        }
        return TrueFalseEnum.FALSE;
    }

    public static boolean getEnumBool(String bool) {
        Assert.notNull(bool, "StatusEnum value is null");
        return TrueFalseEnum.TRUE.getDesc().equalsIgnoreCase(bool);
    }

    /**
     * validValue
     *
     * @param value value
     * @return boolean
     */
    public static boolean isValidValue(String value) {
        return value != null && getEnum(value) != null;
    }

}
