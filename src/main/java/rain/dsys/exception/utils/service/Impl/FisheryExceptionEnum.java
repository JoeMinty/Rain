package rain.dsys.exception.utils.service.Impl;

import rain.dsys.exception.utils.service.ServiceExceptionEnum;

public enum FisheryExceptionEnum implements ServiceExceptionEnum {

	/**
	 * 文件上传
	 */
	FILE_READING_ERROR(400, "FILE_READING_ERROR!"), FILE_NOT_FOUND(400, "FILE_NOT_FOUND!"), UPLOAD_ERROR(500, "上传图片出错");
	/**
	 * 这里可以继续添加自己用到的异常信息
	 *
	 */
	private Integer code;

	private String message;

	FisheryExceptionEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	@Override
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
