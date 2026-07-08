package in.ssf.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SecurityConfig {
    
	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
	    return http
	            .csrf(ServerHttpSecurity.CsrfSpec::disable)
	            .authorizeExchange(exchanges ->
	                    exchanges.anyExchange().permitAll())
	            .build();
	}
	
	@Bean
	public OpenAPI openAPI() {

	    final String securitySchemeName = "bearerAuth";

	    return new OpenAPI()
	            .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
	            .components(
	                    new Components()
	                            .addSecuritySchemes(
	                                    securitySchemeName,
	                                    new SecurityScheme()
	                                            .name(securitySchemeName)
	                                            .type(SecurityScheme.Type.HTTP)
	                                            .scheme("bearer")
	                                            .bearerFormat("JWT")
	                            )
	            );
	}
}