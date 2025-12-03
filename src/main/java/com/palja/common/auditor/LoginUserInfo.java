package com.palja.common.auditor;

import com.palja.common.vo.UserRole;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class LoginUserInfo {

	private String loginId;
	private UserRole role;

}
