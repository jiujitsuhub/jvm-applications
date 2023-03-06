package myjiujitsuacademy.com.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myjiujitsuacademy.com.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;
    @Value("${domain.name}")
    private String domain;

    @PostMapping(
            value = "/auth",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public ResponseEntity auth(@RequestParam Map<String, String> credentials) {
        String jwt = authenticationService.authenticate(credentials.get("email"), credentials.get("password"));
        ResponseCookie cookie = ResponseCookie
                .from("jiujitseiro", jwt)
                .httpOnly(true)
                .path("/")
                .maxAge(100000)
                .domain(domain)
                .build();

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }

    @GetMapping(value = "/auth")
    public ResponseEntity<String> isAuthorized(HttpServletRequest httpServletRequest) {
        String token = getJwtToken(httpServletRequest);
        if (token == null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/");
            return ResponseEntity
                    .status(HttpStatus.TEMPORARY_REDIRECT)
                    .header("Location", "https://login.myjiujitsuacademy.com/")
                    .body("");
        }
        authenticationService.checkTokenValidity(token);
        return ResponseEntity.ok("please continue");
    }

    private String getJwtToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        Optional<Cookie> domainCookie = Arrays.stream(cookies)
                .filter(c -> c.getName().equals("jiujitseiro"))
                .findFirst();
        if (domainCookie.isEmpty()) {
            return null;
        }

        return domainCookie.get().getValue();
    }
}
