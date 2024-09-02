package com.freeying.framework.data.interceptor;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.ParameterUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.freeying.common.core.exception.OrderByException;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * OrderByInnerInterceptor
 * <p>OrderBy插件 对OrderBy传入的参数做一定校验</p>
 *
 * @author fx
 */
public class OrderByInnerInterceptor implements InnerInterceptor {

    private static final List<String> KEYWORDS = Arrays.asList("master", "truncate", "insert", "select", "delete", "update", "declare", "alter", "drop", "sleep");

    @Override
    public boolean willDoQuery(Executor executor, MappedStatement ms,
                               Object parameter, RowBounds rowBounds, ResultHandler resultHandler,
                               BoundSql boundSql) throws SQLException {
        IPage<?> page = ParameterUtils.findPage(parameter).orElse(null);
        if (page == null || page.getSize() < 0 || !page.searchCount()) {
            return true;
        }

        List<OrderItem> orders = page.orders();
        for (OrderItem item : orders) {
            String column = StringUtils.toStringTrim(item.getColumn());
            if (StringUtils.isBlank(column)) {
                // swagger示例查询column是空字符串
                item.setColumn(column);
                return true;
            }
            if (column.contains(StringPool.SPACE)) {
                throw new OrderByException("IllegalOrderByColumn: space " + column);
            }
            if (KEYWORDS.contains(column)) {
                throw new OrderByException("IllegalOrderByColumn: keywords " + column);
            }
            // 进行驼峰转换
            column = underline(column);

            // 获取sql中的 查询字段  判断是否是查询字段中的
            if (!boundSql.getSql().contains(column)) {
                throw new OrderByException("IllegalOrderByColumn:" + column);
            }

            item.setColumn(column);
        }
        return true;
    }

    @Override
    public void beforeQuery(Executor executor, MappedStatement ms,
                            Object parameter, RowBounds rowBounds, ResultHandler resultHandler,
                            BoundSql boundSql) throws SQLException {
        IPage<?> page = ParameterUtils.findPage(parameter).orElse(null);
        if (null == page) {
            return;
        }

        InnerInterceptor.super.beforeQuery(executor, ms, parameter, rowBounds, resultHandler, boundSql);
    }

    /**
     * 驼峰转下划线
     *
     * @param value value
     * @return String
     */
    private String underline(String value) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < value.length(); ++i) {
            char ch = value.charAt(i);
            if (ch >= 'A' && ch <= 'Z') {
                char chCase = (char) (ch + 32);
                if (i > 0) {
                    buf.append('_');
                }
                buf.append(chCase);
            } else {
                buf.append(ch);
            }
        }
        return buf.toString();
    }
}
