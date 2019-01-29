package me.johnniang.api.exception;


import org.springframework.http.HttpStatus;

/**
 * Authorized exception
 *
 * @author johnniang
 */
public class AuthorizedException extends BaseApiException {

    public AuthorizedException(String message) {
        super(message);
    }

    public AuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.UNAUTHORIZED;
    }
}
