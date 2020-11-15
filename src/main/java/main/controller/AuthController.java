package main.controller;

import main.entity.RequestAuth;
import main.service.AuthService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/auth_login", consumes = "application/json", produces = "application/json")
    public void auth(@RequestBody RequestAuth requestAuth, HttpServletResponse httpServletResponse) {
        authService.auth(requestAuth, httpServletResponse);
    }

    @GetMapping(value = "/logout")
    public void logout(@RequestParam String login, HttpServletResponse httpServletResponse) {
        authService.logout(login, httpServletResponse);
    }

    @GetMapping(value = "/ping")
    public void ping() {
    }
}
