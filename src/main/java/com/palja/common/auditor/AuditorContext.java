package com.palja.common.auditor;

public class AuditorContext {

	private static final ThreadLocal<LoginUserInfo> threadLocal = new ThreadLocal<>();

	public static void set(String loginId, String role) {
		threadLocal.set(new LoginUserInfo(loginId, role));
	}

	public static LoginUserInfo get() {
		return threadLocal.get();
	}

	public static void clear() {
		threadLocal.remove();
	}

}
