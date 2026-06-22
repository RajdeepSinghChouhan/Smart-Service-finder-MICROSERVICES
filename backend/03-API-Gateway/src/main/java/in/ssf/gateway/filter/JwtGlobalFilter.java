package in.ssf.gateway.filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import in.ssf.gateway.util.JwtUtil;
import reactor.core.publisher.Mono;

@Component
public class JwtGlobalFilter implements GlobalFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,GatewayFilterChain chain) 
    {
        String path =

        		exchange.getRequest().getURI().getPath();
        if(path.startsWith("/auth")) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest()
                    		.getHeaders()
                    		.getFirst("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")) 
        {
            exchange.getResponse()
                    .setStatusCode(HttpStatus.UNAUTHORIZED);

            return exchange.getResponse().setComplete();
        }

        String token =
            authHeader.substring(7);

        if(!jwtUtil.validateToken(token)) 
        {
            exchange.getResponse()
                    .setStatusCode(HttpStatus.UNAUTHORIZED);

            return exchange.getResponse().setComplete();
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
}