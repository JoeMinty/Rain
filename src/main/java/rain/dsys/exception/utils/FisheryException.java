package rain.dsys.exception.utils;

import rain.dsys.exception.utils.service.ServiceExceptionEnum;

/**
 * 运行时异常
 * 
 * @author Allen
 */
public class FisheryException extends RuntimeException {

	private Integer code;

	private String message;

	public FisheryException(ServiceExceptionEnum exceptionEnum) {
		this.code = exceptionEnum.getCode();
		this.message = exceptionEnum.getMessage();
	}

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
