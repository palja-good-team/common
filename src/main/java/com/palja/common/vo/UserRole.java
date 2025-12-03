package com.palja.common.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {

	MASTER,
	MANAGER,
	CUSTOMER,
	COMPANY_USER

}
