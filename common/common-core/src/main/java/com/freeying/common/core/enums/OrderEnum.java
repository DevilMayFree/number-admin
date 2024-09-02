package com.freeying.common.core.enums;

import com.freeying.common.core.utils.Assert;
import com.freeying.common.core.utils.EnumUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.function.Function;

/**
 * OrderEnum
 * <p>
 * 排序枚举 desc or asc
 * </p>
 *
 * @author fx
 */
public enum OrderEnum implements Serializable {

    /**
     * 正常
     **/
    DESC("desc"),

    /**
     * 删除
     **/
    ASC("asc");

    private final String value;

    private static final Function<String, OrderEnum> FUNC = EnumUtil.lookupMap(OrderEnum.class, OrderEnum::getValue);

    OrderEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    /**
     * getEnumIfPrent or null
     *
     * @param value value
     * @return ElementEnum
     */
    public static OrderEnum getEnum(String value) {
        Assert.notEmpty(value, "OrderEnum value is empty");
        return FUNC.apply(StringUtils.lowerCase(value));
    }

    /**
     * validValue
     *
     * @param value value
     * @return boolean
     */
    public static boolean isValidValue(String value) {
        return StringUtils.isNotBlank(value) && getEnum(value) != null;
    }

}
