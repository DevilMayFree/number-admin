package com.freeying.common.core.enums;

import com.freeying.common.core.utils.Assert;
import com.freeying.common.core.utils.EnumUtil;

import java.io.Serializable;
import java.util.function.Function;

/**
 * DeletedEnum
 * <p>
 * 删除枚举 对应数据库is_delete字段
 * </p>
 *
 * @author fx
 */
public enum DeletedEnum implements Serializable {

    /**
     * 正常
     **/
    NORMAL(0, "正常"),

    /**
     * 删除
     **/
    DELETED(1, "删除");

    private final Integer value;
    private final String desc;

    private static final Function<Integer, DeletedEnum> FUNC = EnumUtil.lookupMap(DeletedEnum.class, DeletedEnum::getValue);

    DeletedEnum(int value, String desc) {
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
    public static DeletedEnum getEnum(Integer value) {
        Assert.notNull(value, "DeletedEnum value is null");
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
