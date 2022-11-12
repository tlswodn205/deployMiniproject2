package site.metacoding.miniproject.handler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import site.metacoding.miniproject.dto.response.CMRespDto;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(RuntimeException.class)
	public CMRespDto<?> error(Exception e){
		return new CMRespDto<>(-1, e.getMessage(), null);
	}
}
