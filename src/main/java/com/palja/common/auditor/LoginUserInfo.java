package com.palja.common.auditor;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class LoginUserInfo {

	private String loginId;
	private String role;

}
