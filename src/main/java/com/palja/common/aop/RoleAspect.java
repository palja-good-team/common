package com.palja.common.aop;

import java.util.Arrays;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.palja.common.annotation.RequiredAnonymous;
import com.palja.common.annotation.RequiredRole;
import com.palja.common.auditor.AuditorContext;
import com.palja.common.auditor.LoginUserInfo;
import com.palja.common.exception.BusinessException;
import com.palja.common.exception.CommonErrorCode;
import com.palja.common.vo.UserRole;

@Aspect
@Component
public class RoleAspect {

	@Before("@annotation(requiredRole)")
	public void requiredRole(RequiredRole requiredRole) {
		LoginUserInfo loginUserInfo = AuditorContext.get();

		if (loginUserInfo == null) {
			throw new BusinessException(CommonErrorCode.FORBIDDEN);
		}

		UserRole role = loginUserInfo.getRole();

		if (!Arrays.asList(requiredRole.value()).contains(role)) {
			throw new BusinessException(CommonErrorCode.FORBIDDEN);
		}
	}

	@Before("@annotation(requiredAnonymous)")
	public void requiredAnonymous(RequiredAnonymous requiredAnonymous) {
		LoginUserInfo loginUserInfo = AuditorContext.get();

		if (loginUserInfo != null) {
			throw new BusinessException(CommonErrorCode.FORBIDDEN);
		}
	}

}
