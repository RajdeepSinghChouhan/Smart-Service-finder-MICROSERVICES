package in.ssf.gateway.filter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import in.ssf.gateway.exception.ExceptionHandlerUtil;
import in.ssf.gateway.util.JwtUtil;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

@Component
public class JwtGlobalFilter implements GlobalFilter {

    @Autowired
    private JwtUtil jwtUtil;
    
    private static final List<String> PUBLIC_PATHS = List.of(
    	    "/auth/**",
    	    "/swagger-ui.html",
    	    "/swagger-ui/**",
    	    "/v3/api-docs/**",
    	    "/auth/v3/api-docs",
    	    "/user/v3/api-docs",
    	    "/provider/v3/api-docs",
    	    "/services/v3/api-docs",
    	    "/booking/v3/api-docs",
    	    "/reviews/v3/api-docs",
    	    "/notifications/v3/api-docs"
    	);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,GatewayFilterChain chain) 
    {
    	
	    	System.out.println("Incoming Path: " + exchange.getRequest().getURI().getPath());
	    	System.out.println("Is Public: " + isPublicPath(exchange.getRequest().getURI().getPath()));
	    	
    		if (exchange.getRequest().getMethod().name().equals("OPTIONS")) {
            return chain.filter(exchange);
        }
    		
        String path = exchange.getRequest().getURI().getPath();

        if (isPublicPath(path)) {
        		System.out.println("Skipping JWT for: " + path);
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        if(authHeader == null || !authHeader.startsWith("Bearer ")) 
        {
            exchange.getResponse()
                    .setStatusCode(HttpStatus.UNAUTHORIZED);

            return exchange.getResponse().setComplete();
        }

        String token =
            authHeader.substring(7);

        try {

            if(!jwtUtil.validateToken(token))
            {
                return ExceptionHandlerUtil.handleUnauthorized(
                        exchange,
                        "Invalid JWT Token");
            }

        }
        catch(Exception e)
        {
            return ExceptionHandlerUtil.handleUnauthorized(
                    exchange,
                    e.getMessage());
        }
        String username =
                jwtUtil.extractUsername(token);

        String role =
                jwtUtil.extractRole(token);
        
        Long userId = 
        		jwtUtil.extractUserId(token);
        
        ServerHttpRequest modifiedRequest =
                exchange.getRequest()
                        .mutate()
                        .header("X-User-Name", username)
                        .header("X-User-Role", role)
                        .header("X-User-Id", userId.toString())
                        .build();
        
        ServerWebExchange modifiedExchange =
                exchange.mutate()
                        .request(modifiedRequest)
                        .build();
        
        return chain.filter(modifiedExchange);
    }
    
    private boolean isPublicPath(String path) {
        return path.startsWith("/auth/")
                || path.equals("/auth")
                || path.startsWith("/swagger-ui")
                || path.equals("/swagger-ui.html")
                || path.endsWith("/v3/api-docs")
                || path.startsWith("/v3/api-docs");
    }
}