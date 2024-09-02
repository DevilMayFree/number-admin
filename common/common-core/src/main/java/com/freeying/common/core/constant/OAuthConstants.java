package com.freeying.common.core.constant;

/**
 * @author fx
 */
public final class OAuthConstants {

    /**
     * 自定义区分UserDetails字段
     */
    public static final String USER_TYPE = "user_type";

    /**
     * 后台UserDetails,授权给后台用户必选的授权之一
     */
    public static final String USER_TYPE_SERVER = "server";

    /**
     * App UserDetails,授权给app用户必选的授权之一
     */
    public static final String USER_TYPE_APP = "app";

    private OAuthConstants() {
    }
}
