package com.freeying.admin.sys.domain.enums;

import com.freeying.common.core.utils.Assert;
import com.freeying.common.core.utils.EnumUtil;

import java.util.function.Function;

/**
 * ResourceTypeEnum
 * <p>资源是否可见枚举</p>
 *
 * @author fx
 */
public enum VisibleEnum {

    /**
     * 显示
     */
    VISIBLE(1, true, "显示"),

    /**
     * 隐藏
     */
    HIDDEN(0, false, "隐藏");

    private final Integer value;
    private final Boolean show;
    private final String label;

    private static final Function<Integer, VisibleEnum> FUNC = EnumUtil.lookupMap(VisibleEnum.class, VisibleEnum::getValue);

    VisibleEnum(Integer value, Boolean show, String label) {
        this.value = value;
        this.show = show;
        this.label = label;
    }

    public Integer getValue() {
        return value;
    }

    public Boolean getShow() {
        return show;
    }

    public Boolean getHidden() {
        return !show;
    }

    public String getLabel() {
        return label;
    }

    /**
     * getEnumIfPrent or null
     *
     * @param value value
     * @return ElementEnum
     */
    public static VisibleEnum getEnum(Integer value) {
        Assert.notNull(value, "VisibleEnum value is null");
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
