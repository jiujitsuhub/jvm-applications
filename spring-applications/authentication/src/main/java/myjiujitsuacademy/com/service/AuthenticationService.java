package myjiujitsuacademy.com.service;

public interface AuthenticationService {
    String authenticate(String email, String password);

    void checkTokenValidity(String token);
}
