package com.palja.common.auditor;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CurrentUser {

	public static String getLoginId() {
		return AuditorContext.get().getLoginId();
	}

	public static String getRole() {
		return AuditorContext.get().getRole();
	}

}
