package com.palja.common.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

	private final CommonErrorCode errorCode;

	public BusinessException(CommonErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}