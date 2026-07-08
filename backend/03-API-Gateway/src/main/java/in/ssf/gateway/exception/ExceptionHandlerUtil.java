package in.ssf.gateway.exception;

import java.time.LocalDateTime;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;

public class ExceptionHandlerUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static Mono<Void> handleUnauthorized(
            ServerWebExchange exchange,
            String message) {

        exchange.getResponse()
                .setStatusCode(HttpStatus.UNAUTHORIZED);

        exchange.getResponse()
                .getHeaders()
                .setContentType(MediaType.APPLICATION_JSON);

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                401,
                "Unauthorized",
                message,
                exchange.getRequest().getPath().value());

        try {

            byte[] bytes =
                    mapper.writeValueAsBytes(error);

            DataBuffer buffer =
                    exchange.getResponse()
                            .bufferFactory()
                            .wrap(bytes);

            return exchange.getResponse()
                    .writeWith(Mono.just(buffer));

        } catch (Exception e) {

            return exchange.getResponse().setComplete();

        }
    }

}