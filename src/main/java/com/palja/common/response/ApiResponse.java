package com.palja.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.palja.common.exception.CommonErrorCode;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(
	boolean success,
	String message,
	T data,
	String code
) {

	/** 성공 응답 생성 (데이터 + 메시지) */
	public static <T> ApiResponse<T> success(T data, String message) {
		return new ApiResponse<>(true, message, data, null);
	}

	/** 성공 응답 생성 (메시지) */
	public static <T> ApiResponse<T> success(String message) {
		return new ApiResponse<>(true, message, null, null);
	}

	/** 실패 응답 생성 (에러 코드 + 에러 메시지) */
	public static <T> ApiResponse<T> error(CommonErrorCode errorCode) {
		return new ApiResponse<>(false, errorCode.getMessage(), null, errorCode.getCode());
	}

	/** 실패 응답 생성 (에러 메시지 + 메시지) */
	public static <T> ApiResponse<T> error(CommonErrorCode errorCode, String message) {
		return new ApiResponse<>(false, errorCode.getMessage() + " (" + message + ")", null, errorCode.getCode());
	}

}
