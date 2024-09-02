package com.freeying.common.core.web;

import java.io.Serializable;

/**
 * 业务代码接口
 *
 * @author fx
 */
public interface IResultCode extends Serializable {

	/**
	 * 消息
	 *
	 * @return String
	 */
	String getMessage();

	/**
	 * 状态码
	 *
	 * @return String
	 */
	String getCode();

}
