package me.johnniang.api.exception;

/**
 * Account expired exception.
 *
 * @author johnniang
 */
public class AccountExpiredException extends BadRequestException {

    public AccountExpiredException(String message) {
        super(message);
    }

    public AccountExpiredException(String message, Throwable cause) {
        super(message, cause);
    }
}
