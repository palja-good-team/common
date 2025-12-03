package com.palja.common.auditor;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.palja.common.vo.UserRole;

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
			AuditorContext.set(request.getHeader("X-USER-LOGIN-ID"), UserRole.valueOf(request.getHeader("X-USER-ROLE")));
			filterChain.doFilter(request, response);
		} finally {
			AuditorContext.clear();
		}
	}

}
