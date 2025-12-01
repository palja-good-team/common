package com.palja.common.auditor;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuditorFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(
		HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
	) throws ServletException, IOException {
		try {
			String loginId = request.getHeader("X-USER-LOGIN-ID");
			AuditorContext.set(loginId);
			filterChain.doFilter(request, response);
		} finally {
			AuditorContext.clear();
		}

	}

}
