package myjiujitsuacademy.com.service.impl;

import com.myjiujitsuacademy.common.domain.model.AppUser;
import com.myjiujitsuacademy.common.domain.repository.AppUserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myjiujitsuacademy.com.exception.AuthenticationException;
import myjiujitsuacademy.com.service.AuthenticationService;
import myjiujitsuacademy.com.token.JwtTokenProvider;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AppUserRepository appUserRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public String authenticate(String email, String password) {
        List<AppUser> appUserList = appUserRepository.findByEmail(email);
        if (appUserList.isEmpty()) {
            throw new AuthenticationException("email-not-found");
        }
        verifyPassword(password, appUserList.get(0));
        return jwtTokenProvider.createToken(email);

    }

    @Override
    public void checkTokenValidity(String token) {
        Claims claims = jwtTokenProvider.parseToken(token);
        Date now = new Date();
        if (claims.getExpiration().toInstant().isBefore(now.toInstant())) {
            throw new AuthenticationException("token-expired");
        }
        log.info("token is fine token: {}", token);
    }

    private void verifyPassword(String password, AppUser appUser) {
        if (!BCrypt.checkpw(password, appUser.getPassword())) {
            throw new AuthenticationException("wrong-password");
        }
    }


}
