package com.freeying.common.core.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * 分页模型
 *
 * @author fx
 */
@SuppressWarnings({"squid:S2387", "squid:S1948", "squid:S1144"})
@Schema(description = "分页返回对象")
public class PageInfo<T> implements IPage<T> {

    /**
     * 查询数据列表
     */
    @Schema(description = "查询数据列表")
    protected List<T> records = Collections.emptyList();

    /**
     * 总数
     */
    @Schema(description = "总数")
    protected long total = 0;
    /**
     * 每页显示条数，默认 10
     */
    @Schema(description = "每页显示条数")
    protected long size = 10;

    /**
     * 当前页
     */
    @Schema(description = "当前页")
    protected long current = 1;

    /**
     * 排序字段信息
     */
    @Schema(description = "排序字段")
    protected List<OrderItem> orders = new ArrayList<>();

    public void setOrders(List<OrderItem> orders) {
        this.orders = orders;
    }

    /**
     * 自动优化 COUNT SQL
     */
    @JsonIgnore
    protected boolean optimizeCountSql = true;
    /**
     * 是否进行 count 查询
     */
    @JsonIgnore
    protected boolean searchCount = true;
    /**
     * {@link #optimizeJoinOfCountSql()}
     */
    @JsonIgnore
    protected boolean optimizeJoinOfCountSql = true;

    public void setOptimizeJoinOfCountSql(boolean optimizeJoinOfCountSql) {
        this.optimizeJoinOfCountSql = optimizeJoinOfCountSql;
    }

    /**
     * countId
     */
    @JsonIgnore
    protected String countId;

    public void setCountId(String countId) {
        this.countId = countId;
    }

    /**
     * countId
     */
    @JsonIgnore
    protected Long maxLimit;

    public void setMaxLimit(Long maxLimit) {
        this.maxLimit = maxLimit;
    }

    public PageInfo() {
    }

    /**
     * 分页构造函数
     *
     * @param current 当前页
     * @param size    每页显示条数
     */
    public PageInfo(long current, long size) {
        this(current, size, 0);
    }

    public PageInfo(long current, long size, long total) {
        this(current, size, total, true);
    }

    public PageInfo(long current, long size, boolean searchCount) {
        this(current, size, 0, searchCount);
    }

    public PageInfo(long current, long size, long total, boolean searchCount) {
        if (current > 1) {
            this.current = current;
        }
        this.size = size;
        this.total = total;
        this.searchCount = searchCount;
    }

    /**
     * 是否存在上一页
     *
     * @return true / false
     */
    public boolean hasPrevious() {
        return this.current > 1;
    }

    /**
     * 是否存在下一页
     *
     * @return true / false
     */
    public boolean hasNext() {
        return this.current < this.getPages();
    }

    @Override
    public List<T> getRecords() {
        return this.records;
    }

    @Override
    public PageInfo<T> setRecords(List<T> records) {
        this.records = records;
        return this;
    }

    @Override
    public long getTotal() {
        return this.total;
    }

    @Override
    public PageInfo<T> setTotal(long total) {
        this.total = total;
        return this;
    }

    @Override
    public long getSize() {
        return this.size;
    }

    @Override
    public PageInfo<T> setSize(long size) {
        this.size = size;
        return this;
    }

    @Override
    public long getCurrent() {
        return this.current;
    }

    @Override
    public PageInfo<T> setCurrent(long current) {
        this.current = current;
        return this;
    }

    @Override
    public String countId() {
        return this.countId;
    }

    @Override
    public Long maxLimit() {
        return this.maxLimit;
    }

    /**
     * 查找 order 中正序排序的字段数组
     *
     * @param filter 过滤器
     * @return 返回正序排列的字段数组
     */
    private String[] mapOrderToArray(Predicate<OrderItem> filter) {
        List<String> columns = new ArrayList<>(orders.size());
        orders.forEach(i -> {
            if (filter.test(i)) {
                columns.add(i.getColumn());
            }
        });
        return columns.toArray(new String[0]);
    }

    /**
     * 移除符合条件的条件
     *
     * @param filter 条件判断
     */
    private void removeOrder(Predicate<OrderItem> filter) {
        for (int i = orders.size() - 1; i >= 0; i--) {
            if (filter.test(orders.get(i))) {
                orders.remove(i);
            }
        }
    }

    /**
     * 添加新的排序条件，构造条件可以使用工厂
     *
     * @param items 条件
     * @return 返回分页参数本身
     */
    public PageInfo<T> addOrder(OrderItem... items) {
        orders.addAll(Arrays.asList(items));
        return this;
    }

    /**
     * 添加新的排序条件，构造条件可以使用工厂
     *
     * @param items 条件
     * @return 返回分页参数本身
     */
    public PageInfo<T> addOrder(List<OrderItem> items) {
        orders.addAll(items);
        return this;
    }

    @Override
    public List<OrderItem> orders() {
        return this.orders;
    }

    @Override
    public boolean optimizeCountSql() {
        return optimizeCountSql;
    }

    public static <T> PageInfo<T> of(long current, long size, long total, boolean searchCount) {
        return new PageInfo<>(current, size, total, searchCount);
    }

    @Override
    public boolean optimizeJoinOfCountSql() {
        return optimizeJoinOfCountSql;
    }

    public PageInfo<T> setSearchCount(boolean searchCount) {
        this.searchCount = searchCount;
        return this;
    }

    public PageInfo<T> setOptimizeCountSql(boolean optimizeCountSql) {
        this.optimizeCountSql = optimizeCountSql;
        return this;
    }

    @Override
    @Schema(description = "分页总页数")
    public long getPages() {
        // 解决 github issues/3208
        return IPage.super.getPages();
    }

    /* --------------- 以下为静态构造方式 --------------- */
    public static <T> PageInfo<T> of(long current, long size) {
        return of(current, size, 0);
    }

    public static <T> PageInfo<T> of(long current, long size, long total) {
        return of(current, size, total, true);
    }

    public static <T> PageInfo<T> of(long current, long size, boolean searchCount) {
        return of(current, size, 0, searchCount);
    }

    @Override
    public boolean searchCount() {
        if (total < 0) {
            return false;
        }
        return searchCount;
    }

    public String getCountId() {
        return this.countId;
    }

    public Long getMaxLimit() {
        return this.maxLimit;
    }

    public List<OrderItem> getOrders() {
        return this.orders;
    }

    public boolean isOptimizeCountSql() {
        return this.optimizeCountSql;
    }

    public boolean isSearchCount() {
        return this.searchCount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("records", records)
                .append("total", total)
                .append("size", size)
                .append("current", current)
                .append("orders", orders)
                .append("optimizeCountSql", optimizeCountSql)
                .append("searchCount", searchCount)
                .append("optimizeJoinOfCountSql", optimizeJoinOfCountSql)
                .append("countId", countId)
                .append("maxLimit", maxLimit)
                .toString();
    }
}

