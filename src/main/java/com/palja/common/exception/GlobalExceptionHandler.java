package com.palja.common.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.palja.common.response.ApiResponse;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	/**
	 * 비즈니스 규칙 위반 예외 처리
	 */
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException e) {
		log.error("BusinessException: {}", e.getMessage());
		ErrorCode errorCode = e.getErrorCode();

		return ResponseEntity
			.status(errorCode.getHttpStatus())
			.body(ApiResponse.error(errorCode));
	}

	/**
	 * 입력 검증 실패 예외 처리 (Spring Validation)
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<Void>> handleValidationException(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(err -> {
			String field = (err instanceof FieldError fe) ? fe.getField() : err.getObjectName();
			errors.put(field, err.getDefaultMessage());
		});
		log.warn("Validation failed: {}", errors);

		return ResponseEntity
			.badRequest()
			.body(ApiResponse.error(CommonErrorCode.INVALID_INPUT_VALUE, errors.toString()));
	}

	/**
	 * 도메인 계층 예외 처리
	 */
	@ExceptionHandler({IllegalStateException.class, IllegalArgumentException.class})
	public ResponseEntity<ApiResponse<Void>> handleDomainException(RuntimeException e) {
		log.warn("DomainException: {}", e.getMessage());

		return ResponseEntity
			.status(CommonErrorCode.DOMAIN_ERROR.getHttpStatus())
			.body(ApiResponse.error(CommonErrorCode.DOMAIN_ERROR, e.getMessage()));
	}

	/**
	 * 요청 본문(JSON) 파싱 실패 예외 처리
	 * - 잘못된 UUID, Enum, 숫자 형식 등 역직렬화 오류 대응
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApiResponse<Void>> handleJsonParseException(HttpMessageNotReadableException e) {
		log.warn("JSON parse error: {}", e.getMessage());
		return ResponseEntity
			.badRequest()
			.body(ApiResponse.error(CommonErrorCode.INVALID_INPUT_VALUE, "형식을 확인해주세요."));
	}

	/**
	 * 파라미터 타입 불일치 예외 처리
	 */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ApiResponse<?>> handleTypeMismatch(MethodArgumentTypeMismatchException e) {
		if ("X-USER-ROLE".equalsIgnoreCase(e.getName())) {
			return ResponseEntity
				.status(CommonErrorCode.INVALID_HEADER_USER_ROLE.getHttpStatus())
				.body(ApiResponse.error(CommonErrorCode.INVALID_HEADER_USER_ROLE, "value=" + e.getValue()));
		}
		return ResponseEntity
			.badRequest()
			.body(ApiResponse.error(CommonErrorCode.BAD_REQUEST, e.getMessage()));
	}

	/**
	 * FeignClient 통신 예외 처리
	 */
	@ExceptionHandler(FeignException.class)
	public ResponseEntity<ApiResponse<Void>> handleFeign(FeignException e) {
		int status = e.status();
		log.error("[Feign] status={} message={}", status, e.getMessage());

		return ResponseEntity
			.status(status)
			.body(ApiResponse.error(CommonErrorCode.FEIGN_ERROR, e.getMessage()));
	}

	/**
	 * 요청에 헤더가 존재하지 않을 때 예외 처리
	 */
	@ExceptionHandler(MissingRequestHeaderException.class)
	public ResponseEntity<ApiResponse<Void>> handleMissingHeader(MissingRequestHeaderException e) {
		log.error("Not found request header : {}",  e.getMessage());

		return ResponseEntity
			.badRequest()
			.body(ApiResponse.error(CommonErrorCode.MISSING_REQUEST_HEADER));
	}

	/**
	 * 서버 내부 오류 예외 처리
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
		log.error("Unexpected exception", e);

		return ResponseEntity
			.internalServerError()
			.body(ApiResponse.error(CommonErrorCode.INTERNAL_SERVER_ERROR));
	}

}
