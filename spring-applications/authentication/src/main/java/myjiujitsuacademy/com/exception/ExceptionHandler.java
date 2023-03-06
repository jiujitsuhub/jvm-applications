package myjiujitsuacademy.com.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.view.RedirectView;

@ControllerAdvice
@Slf4j
public class ExceptionHandler extends ResponseEntityExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(InvalidTokenException.class)
    public RedirectView handleInvalidTokeException(InvalidTokenException e, WebRequest webRequest) {
        log.error("invalid token exception", e);
        return redirectWithFeedback(e.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(AuthenticationException.class)
    public RedirectView handleAuthException(AuthenticationException e, WebRequest webRequest) {
        log.warn("auth exception", e);
        return redirectWithFeedback(e.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public RedirectView handleGenericException(Exception e, WebRequest webRequest) {
        log.error("Caught unhandled exception", e);
        return redirectWithFeedback(e.getMessage());
    }

    public RedirectView redirectWithFeedback(String message) {
        RedirectView redirectView = new RedirectView("https://login.myjiujitsuacademy.com/");
        redirectView.getAttributesMap().put("feedback", message);
        return redirectView;

    }


}
