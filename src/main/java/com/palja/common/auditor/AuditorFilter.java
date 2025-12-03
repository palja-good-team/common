package com.palja.common.auditor;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.palja.common.vo.UserRole;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuditorFilter extends OncePerRequestFilter {

	private final Map<String, List<String>> permitAllPaths = Map.of(
		"/api/v1/auth/login", List.of("POST"),
		"/api/v1/auth/refresh", List.of("POST"),
		"/api/v1/customers", List.of("POST"),
		"/api/v1/company-users", List.of("POST")
	);

	@Override
	protected void doFilterInternal(
		HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
	) throws ServletException, IOException {
		try {
			String path = request.getServletPath();
			String method = request.getMethod();

			String loginId = request.getHeader("X-USER-LOGIN-ID");
			String role = request.getHeader("X-USER-ROLE");

			if (loginId == null || role == null) {
				if (permitAllPaths.containsKey(path) && permitAllPaths.get(path).contains(method)) {
					filterChain.doFilter(request, response);
					return;
				}
			}

			AuditorContext.set(loginId, UserRole.valueOf(role));
			filterChain.doFilter(request, response);
		} finally {
			AuditorContext.clear();
		}
	}

}
