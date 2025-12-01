package com.palja.common.auditor;

public class AuditorContext {

	private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

	public static void set(String loginId) {
		threadLocal.set(loginId);
	}

	public static String get() {
		return threadLocal.get();
	}

	public static void clear() {
		threadLocal.remove();
	}

}
