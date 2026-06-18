package in.ssf.auth.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.ssf.auth.dto.AuthResponseDto;
import in.ssf.auth.dto.LoginRequestDto;
import in.ssf.auth.dto.RegisterRequestDto;
import in.ssf.auth.service.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthRestController {

    @Autowired
    private JwtService service;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequestDto request){
        return service.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginRequestDto request)
    {    
        return ResponseEntity.ok(service.login(request));
    }
}
//jakarta.servlet.ServletException: Request processing failed: java.lang.NullPointerException:
//Cannot invoke "in.ssf.auth.dto.AuthResponseDto.getToken()" because "response" is null





