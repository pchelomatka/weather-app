package main.service;

import main.entity.Auth;
import main.entity.RequestAuth;
import main.repository.AuthRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
public class AuthService {

    private final AuthRepository authRepository;

    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public void auth(RequestAuth requestAuth, HttpServletResponse httpServletResponse) {
        Auth auth = authRepository.findByLogin(requestAuth.getLogin());
        if (!(auth == null) && auth.getPassword().equals(requestAuth.getPassword())) {
            auth.setAuthFlag(true);
            authRepository.save(auth);
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        } else {
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    public void logout(String login, HttpServletResponse httpServletResponse) {
        Auth auth = authRepository.findByLogin(login);
        auth.setAuthFlag(false);
        authRepository.save(auth);
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
    }
}
