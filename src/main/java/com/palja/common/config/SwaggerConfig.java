package com.palja.common.config;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.palja.common.annotation.RequiredAnonymous;
import com.palja.common.annotation.RequiredRole;
import com.palja.common.vo.UserRole;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;

@OpenAPIDefinition(
	info = @Info(
		title = "Palja",
		description = "Palja Service의 REST API 명세서입니다.",
		version = "v1.0.0"
	)
)
@SecurityScheme(
	name = "Authorization",
	type = SecuritySchemeType.HTTP,
	bearerFormat = "JWT",
	scheme = "bearer",
	in = SecuritySchemeIn.HEADER
)
@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
			.addServersItem(new Server().url("/"));
	}

	@Bean
	public OperationCustomizer operationCustomizer() {
		return (operation, handlerMethod) -> {
			if (operation.getParameters() != null) {
				operation.setParameters(
					operation.getParameters().stream()
						.filter(p -> !p.getName().equals("Authorization"))
						.filter(p -> !p.getName().equals("refresh_token"))
						.toList()
				);
			}

			StringBuilder sb = new StringBuilder("\n\n");

			RequiredRole requiredRole =  handlerMethod.getMethodAnnotation(RequiredRole.class);
			if(requiredRole != null) {
				sb.append("\uD83D\uDD12 권한이 필요합니다. \n\n");
				String roles = Arrays.stream(requiredRole.value())
					.map(UserRole::name)
					.map(r -> " - " + r)
					.collect(Collectors.joining("\n\n"));
				sb.append(roles);
			}

			RequiredAnonymous requiredAnonymous = handlerMethod.getMethodAnnotation(RequiredAnonymous.class);
			if(requiredAnonymous != null) {
				sb.append("\uD83D\uDD12 권한이 필요합니다. \n\n");
				sb.append(" - ANONYMOUS");
			}

			if (requiredRole == null && requiredAnonymous == null) {
				sb.append("\uD83D\uDD13 권한이 필요하지 않습니다.");
			}

			String description = operation.getDescription() != null ? "### " +operation.getDescription() : "";

			operation.setDescription(description + sb.toString());

			return operation;
		};
	}

}
