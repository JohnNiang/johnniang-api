package me.johnniang.api.exception;

import org.springframework.http.HttpStatus;

/**
 * Occur when domain is not found.
 *
 * @author johnniang
 */
public class NotFoundException extends BaseApiException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
