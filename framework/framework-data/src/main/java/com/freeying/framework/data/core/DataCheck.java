package com.freeying.framework.data.core;

/**
 * DataCheck
 * <p>持久化结果校验</p>
 *
 * @author fx
 */
public final class DataCheck {

    private DataCheck() {
    }

    /**
     * 校验影响条数是否唯一
     *
     * @param result 影响条数
     * @return boolean
     */
    public static boolean limitOne(int result) {
        return result == 1;
    }

    /**
     * 批量校验
     *
     * @param len    操作行数
     * @param result 影响条数
     * @return boolean
     */
    public static boolean batch(int len, int result) {
        return len == result;
    }

    /**
     * insert校验
     * <p>插入n条记录，返回影响行数n。（n>=1，n为0时实际为插入失败）</p>
     *
     * @param result 影响行数
     * @return boolean
     */
    public static boolean insert(int result) {
        return result >= 1;
    }

    /**
     * update校验
     * <p>更新n条记录，返回影响行数n。（n>=0）</p>
     *
     * @param result 影响行数
     * @return boolean
     */
    public static boolean update(int result) {
        return result > 0;
    }

    /**
     * delete校验
     * <p>删除n条记录，返回影响行数n。（n>=0）</p>
     *
     * @param result 影响行数
     * @return boolean
     */
    public static boolean delete(int result) {
        return result > 0;
    }

    /**
     * 批量delete校验
     * <p>删除len条记录，返回影响行数n。（len == n）</p>
     *
     * @param len    操作行数
     * @param result 影响行数
     * @return boolean
     */
    public static boolean batchDelete(int len, int result) {
        return len == result;
    }
}
