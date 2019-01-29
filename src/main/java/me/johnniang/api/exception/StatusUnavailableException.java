package me.johnniang.api.exception;

import org.springframework.http.HttpStatus;

/**
 * Status unavailable exception
 *
 * @author johnniang
 */
public class StatusUnavailableException extends BaseApiException {

    public StatusUnavailableException(String message) {
        super(message);
    }

    public StatusUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
