package com.freeying.framework.data.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.freeying.common.core.exception.BadRequestException;
import com.freeying.framework.security.utils.SecurityUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectionException;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.function.Supplier;

/**
 * 自动填充
 *
 * @author fx
 */
public class CustomMetaObjectHandler implements MetaObjectHandler {

    private static final String CREATE_BY = "createBy";
    private static final String UPDATE_BY = "updateBy";
    private static final String GMT_CREATE = "gmtCreate";
    private static final String GMT_MODIFIED = "gmtModified";

    /**
     * 插入数据时填充数据
     *
     * @param metaObject metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        try {
            long operator = SecurityUtil.getCurrentUserId();
            fillValue(metaObject, CREATE_BY, () -> operator);
            fillValue(metaObject, UPDATE_BY, () -> operator);
        } catch (BadRequestException e) {
            // ignore exception
        }
        fillValue(metaObject, GMT_CREATE, () -> getDateValue(metaObject.getSetterType(GMT_CREATE)));
        fillValue(metaObject, GMT_MODIFIED, () -> getDateValue(metaObject.getSetterType(GMT_MODIFIED)));

    }

    /**
     * 更新数据时填充数据
     *
     * @param metaObject metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        try {
            long operator = SecurityUtil.getCurrentUserId();
            fillValue(metaObject, UPDATE_BY, () -> operator);
        } catch (BadRequestException e) {
            // ignore exception
        }
        fillValue(metaObject, GMT_MODIFIED, () -> getDateValue(metaObject.getSetterType(GMT_MODIFIED)));
    }

    /**
     * 填充字段
     *
     * @param metaObject    metaObject
     * @param fieldName     fieldName
     * @param valueSupplier valueSupplier
     */
    private void fillValue(MetaObject metaObject, String fieldName, Supplier<Object> valueSupplier) {
        if (!metaObject.hasGetter(fieldName)) {
            return;
        }
        Object sidObj = metaObject.getValue(fieldName);
        if (sidObj == null && metaObject.hasSetter(fieldName) && valueSupplier != null) {
            try {
                setFieldValByName(fieldName, valueSupplier.get(), metaObject);
            } catch (ReflectionException e) {
                // ignore exception
            }
        }
    }

    /**
     * 获取最新的日期
     *
     * @param setterType setterType
     * @return Object
     */
    private Object getDateValue(Class<?> setterType) {
        if (Date.class.equals(setterType)) {
            return new Date();
        } else if (LocalDateTime.class.equals(setterType)) {
            return LocalDateTime.now();
        }
        return null;
    }
}
