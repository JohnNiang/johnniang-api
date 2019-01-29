package me.johnniang.api.exception;

import org.springframework.http.HttpStatus;

/**
 * Service exception
 *
 * @author johnniang
 */
public class ServiceException extends BaseApiException {

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
