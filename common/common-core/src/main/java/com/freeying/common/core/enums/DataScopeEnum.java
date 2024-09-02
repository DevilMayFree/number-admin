package com.freeying.common.core.enums;

/**
 * <p>
 * 数据权限枚举
 * </p>
 *
 * @author fx
 */
public enum DataScopeEnum {

    /* 全部的数据权限 */
    ALL("全部", "全部的数据权限"),

    /* 自己部门的数据权限 */
    THIS_LEVEL("本级", "自己部门的数据权限"),

    /* 自定义的数据权限 */
    CUSTOMIZE("自定义", "自定义的数据权限");

    private final String value;
    private final String description;

    DataScopeEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public static DataScopeEnum find(String val) {
        for (DataScopeEnum dataScopeEnum : DataScopeEnum.values()) {
            if (dataScopeEnum.getValue().equals(val)) {
                return dataScopeEnum;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
