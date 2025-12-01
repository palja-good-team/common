package com.palja.common.auditor;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component
public class AuditorAwareImpl implements AuditorAware<LoginUserInfo> {

	@Override
	public Optional<LoginUserInfo> getCurrentAuditor() {
		return Optional.ofNullable(AuditorContext.get());
	}

}
