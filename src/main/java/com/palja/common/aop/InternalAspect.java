package com.palja.common.aop;

import java.util.Objects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.palja.common.annotation.RequiredInternal;
import com.palja.common.exception.BusinessException;
import com.palja.common.exception.CommonErrorCode;

import jakarta.servlet.http.HttpServletRequest;

@Aspect
@Component
public class InternalAspect {

	private final String internalSecretKey = System.getenv("INTERNAL_SECRET_KEY");

	@Before("@annotation(requiredInternal)")
	public void requiredInternal(RequiredInternal requiredInternal) {
		HttpServletRequest request = ((ServletRequestAttributes)(Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))).getRequest();
		String headerSecretKey = request.getHeader("X-INTERNAL-SECRET-KEY");

		if (!Objects.equals(internalSecretKey, headerSecretKey)) {
			throw new BusinessException(CommonErrorCode.FORBIDDEN);
		}
	}

}
