package com.freeying.admin.sys.domain.enums;

import com.freeying.common.core.utils.Assert;
import com.freeying.common.core.utils.EnumUtil;

import java.io.Serializable;
import java.util.function.Function;

/**
 * DictTypeEnum
 * <p>字典类型枚举</p>
 *
 * @author fx
 */
public enum DictTypeEnum implements Serializable {

    /**
     * 系统类型
     **/
    SYSTEM(0, "系统类型"),

    /**
     * 业务类型
     **/
    BUSINESS(1, "业务类型");

    private final Integer value;
    private final String remark;

    private static final Function<Integer, DictTypeEnum> FUNC = EnumUtil.lookupMap(DictTypeEnum.class, DictTypeEnum::getValue);

    DictTypeEnum(int value, String remark) {
        this.value = value;
        this.remark = remark;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return remark;
    }

    /**
     * getEnumIfPrent or null
     *
     * @param value value
     * @return ElementEnum
     */
    public static DictTypeEnum getEnum(Integer value) {
        Assert.notNull(value, "DictTypeEnum value is null");
        return FUNC.apply(value);
    }

    /**
     * validValue
     *
     * @param value value
     * @return boolean
     */
    public static boolean isValidValue(Integer value) {
        if (null == value) {
            return false;
        }
        return getEnum(value) != null;
    }

}
