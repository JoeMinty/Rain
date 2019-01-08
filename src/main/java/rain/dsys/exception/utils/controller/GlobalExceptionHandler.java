package rain.dsys.exception.utils.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import rain.dsys.exception.utils.FisheryException;
import rain.dsys.exception.utils.service.Impl.FisheryExceptionEnum;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/**
	 * 拦截业务异常
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler(FisheryException.class)
	@ResponseBody
	public String notFound(FisheryException e) {
		LOGGER.error("业务异常", e);
		return e.getMessage();
	}

	/**
	 * 拦截未知的运行时异常
	 *
	 * @param e
	 * @return
	 */
	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
	public String notFound(RuntimeException e) {
		LOGGER.error("运行时异常：", e);
		return FisheryExceptionEnum.UPLOAD_ERROR.getMessage();
	}
}
