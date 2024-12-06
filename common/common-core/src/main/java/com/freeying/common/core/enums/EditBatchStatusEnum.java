package com.freeying.common.core.enums;

import com.freeying.common.core.utils.Assert;
import com.freeying.common.core.utils.EnumUtil;

import java.io.Serializable;
import java.util.function.Function;

/**
 * EditBatchStatusEnum
 *
 * @author fx
 */
public enum EditBatchStatusEnum implements Serializable {

    /**
     * 批量编辑成功
     **/
    SUCCESS(1, "批量编辑成功"),

    /**
     * 批量编辑列表为空
     */
    EMPTY(2,"批量编辑列表为空"),

    /**
     * 续费天数必须为正整数
     */
    ERROR_DAYS(3,"续费天数必须为正整数"),

    /**
     * 存在剩余天数超过15天的客户
     **/
    HAS_OVER_15_DAY(4,  "存在剩余天数超过15天的客户"),

    /**
     * 批量续费失败
     */
    ERROR(5,"批量续费失败");

    private final Integer value;
    private final String desc;

    private static final Function<Integer, EditBatchStatusEnum> FUNC = EnumUtil.lookupMap(EditBatchStatusEnum.class, EditBatchStatusEnum::getValue);

    EditBatchStatusEnum(int value, String desc) {
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
    public static EditBatchStatusEnum getEnum(Integer value) {
        Assert.notNull(value, "EditBatchStatusEnum value is null");
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
