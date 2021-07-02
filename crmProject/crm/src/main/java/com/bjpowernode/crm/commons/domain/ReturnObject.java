package com.bjpowernode.crm.commons.domain;

/**
 * @author lzx
 * @create 2021/6/4 19:01
 */
public class ReturnObject {
	/**
	 * 1表示成功,0表示失败
	 */
	private String code;
	/**
	 * 描述信息
	 */
	private String message;
	private Object retData;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getRetData() {
		return retData;
	}

	public void setRetData(Object retData) {
		this.retData = retData;
	}
}
