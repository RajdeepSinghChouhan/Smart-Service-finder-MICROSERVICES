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
import in.ssf.auth.dto.RegisterResponseDto;
import in.ssf.auth.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@Tag(name = "User APIs", description = "Operations related to users")
public class AuthRestController {

    @Autowired
    private JwtService service;

    @PostMapping("/register")
    @Operation(summary = "Register The User or Provider")
    public RegisterResponseDto register(@Valid @RequestBody RegisterRequestDto request){
        return service.register(request);
    }

    @PostMapping("/login")
    @Operation(summary = "Login User or Provider")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginRequestDto request)
    {    
        return ResponseEntity.ok(service.login(request));
    }
}




