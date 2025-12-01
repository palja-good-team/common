package com.palja.common.auditor;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		System.out.println("Auditor를 설정합니다.");
		return Optional.ofNullable(AuditorContext.get());
	}

}
