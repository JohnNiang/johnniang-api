package me.johnniang.api.exception;

import org.springframework.http.HttpStatus;

/**
 * Bad request exception.
 *
 * @author johnniang
 */
public class BadRequestException extends BaseApiException {


    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
