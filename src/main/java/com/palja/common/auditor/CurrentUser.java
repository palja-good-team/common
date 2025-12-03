package com.palja.common.auditor;

import com.palja.common.vo.UserRole;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CurrentUser {

	public static String getLoginId() {
		return AuditorContext.get().getLoginId();
	}

	public static UserRole getRole() {
		return AuditorContext.get().getRole();
	}

}
