package com.palja.common.auditor;

import com.palja.common.vo.UserRole;

public class AuditorContext {

	private static final ThreadLocal<LoginUserInfo> threadLocal = new ThreadLocal<>();

	public static void set(String loginId, UserRole role) {
		threadLocal.set(new LoginUserInfo(loginId, role));
	}

	public static LoginUserInfo get() {
		return threadLocal.get();
	}

	public static void clear() {
		threadLocal.remove();
	}

}
