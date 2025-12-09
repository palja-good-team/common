package com.palja.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.palja.common.auditor.AuditorContext;

import feign.RequestInterceptor;

@Configuration
public class FeignClientConfig {

	@Bean
	public RequestInterceptor requestInterceptor() {
		return requestTemplate -> {
			requestTemplate.header("X-USER-LOGIN-ID", AuditorContext.get().getLoginId());
			requestTemplate.header("X-USER-ROLE", AuditorContext.get().getRole().name());
		};
	}

}
