package com.freeying.admin.sys.domain.enums;

import com.freeying.common.core.utils.Assert;
import com.freeying.common.core.utils.EnumUtil;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * ResourceTypeEnum
 * <p>资源类型枚举</p>
 *
 * @author fx
 */
public enum ResourceTypeEnum {

    /**
     * 菜单,菜单下只能存在权限类型数据
     */
    MENU(1, "菜单", List.of(3)),

    /**
     * 目录,目录下可存在菜单、目录和外链
     */
    CONTENTS(2, "目录", List.of(1, 2, 4)),

    /**
     * 权限,无
     */
    PERMISSION(3, "权限", List.of()),

    /**
     * 外链,无
     */
    LINK(4, "外链", List.of()),
    ;

    /**
     * 值
     */
    private final Integer value;

    /**
     * 文本
     */
    private final String label;

    /**
     * 下级可以存在的资源类型
     */
    private final List<Integer> limit;

    private static final Function<Integer, ResourceTypeEnum> FUNC = EnumUtil.lookupMap(ResourceTypeEnum.class, ResourceTypeEnum::getValue);

    ResourceTypeEnum(Integer value, String label, List<Integer> limit) {
        this.value = value;
        this.label = label;
        this.limit = limit;
    }

    public Integer getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    public List<Integer> getLimit() {
        return limit;
    }

    /**
     * 获取菜单类型
     *
     * @return list
     */
    public static List<Integer> getMenus() {
        return Arrays.asList(MENU.value, CONTENTS.value, LINK.value);
    }

    /**
     * 获取全部资源类型
     *
     * @return list
     */
    public static List<Integer> getTreeSelect() {
        return Arrays.asList(MENU.value, CONTENTS.value, PERMISSION.value, LINK.value);
    }

    /**
     * 获取权限类型
     *
     * @return list
     */
    public static List<Integer> getPermission() {
        return Arrays.asList(MENU.value, PERMISSION.value);
    }

    /**
     * getEnumIfPrent or null
     *
     * @param value value
     * @return ElementEnum
     */
    public static ResourceTypeEnum getEnum(Integer value) {
        Assert.notNull(value, "ResourceTypeEnum value is null");
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

    /**
     * validValue
     *
     * @param value value
     * @return boolean
     */
    public static boolean isValidLimit(Integer value, Integer check) {
        if (null == value || null == check) {
            return false;
        }
        if (getEnum(check) == null) {
            return false;
        }
        ResourceTypeEnum anEnum = getEnum(value);
        if (anEnum == null) {
            return false;
        }
        return anEnum.getLimit().contains(check);
    }
}
