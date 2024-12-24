package com.freeying.common.core.enums;

import com.freeying.common.core.utils.Assert;
import com.freeying.common.core.utils.EnumUtil;

import java.io.Serializable;
import java.util.function.Function;

/**
 * EditBatchTipsStatusEnum
 *
 * @author fx
 */
public enum EditBatchTipsStatusEnum implements Serializable {

    /**
     * 批量编辑列表为空
     */
    EMPTY(1, "批量编辑列表为空"),

    /**
     * 续费天数必须为正整数
     */
    ERROR_DAYS(2, "续费天数必须为正整数"),

    /**
     * 存在被别人正在编辑中的数据
     **/
    HAS_OTHER_EDIT(3, "存在被别人正在编辑中的数据"),

    /**
     * 后台查询出错
     */
    ERROR(4, "后台查询出错"),

    /**
     * 可以编辑
     */
    CAN_EDIT(5, "可以编辑");

    private final Integer value;
    private final String desc;

    private static final Function<Integer, EditBatchTipsStatusEnum> FUNC =
            EnumUtil.lookupMap(EditBatchTipsStatusEnum.class, EditBatchTipsStatusEnum::getValue);

    EditBatchTipsStatusEnum(int value, String desc) {
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
    public static EditBatchTipsStatusEnum getEnum(Integer value) {
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
