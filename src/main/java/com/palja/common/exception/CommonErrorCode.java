package com.palja.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {

	DOMAIN_ERROR(HttpStatus.BAD_REQUEST, "도메인 규칙을 위반했습니다."),
	INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "잘못된 입력값입니다"),
	FORBIDDEN(HttpStatus.FORBIDDEN, "권한이 없습니다"),
	BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다"),
	INVALID_HEADER_USER_ROLE(HttpStatus.BAD_REQUEST, "잘못된 X-User-Role 헤더입니다"),
	NOT_FOUND(HttpStatus.NOT_FOUND, "리소스를 찾을 수 없습니다."),
	FEIGN_ERROR(HttpStatus.BAD_GATEWAY, "외부 서비스 요청 중 오류가 발생했습니다."),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다"),
	MISSING_REQUEST_HEADER(HttpStatus.BAD_REQUEST, "요청에서 헤더를 찾을 수 없습니다."),
	;

	private final HttpStatus httpStatus;
	private final String message;

}
