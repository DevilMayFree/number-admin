package com.freeying.common.core.enums;

import com.freeying.common.core.utils.Assert;
import com.freeying.common.core.utils.EnumUtil;

import java.io.Serializable;
import java.util.function.Function;

/**
 * ContentStatusEnum
 *
 * @author fx
 */
public enum ContentStatusEnum implements Serializable {

    DONE(2, "已确定"),
    DO(1, "领取未确定"),
    NONE(0, "未领取");

    private final Integer value;
    private final String desc;

    private static final Function<Integer, ContentStatusEnum> FUNC = EnumUtil.lookupMap(ContentStatusEnum.class, ContentStatusEnum::getValue);

    ContentStatusEnum(int value, String desc) {
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
    public static ContentStatusEnum getEnum(Integer value) {
        Assert.notNull(value, "StatusEnum value is null");
        return FUNC.apply(value);
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
