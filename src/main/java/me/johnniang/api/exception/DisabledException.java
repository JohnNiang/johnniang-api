package me.johnniang.api.exception;

/**
 * Disabled exception.
 *
 * @author johnniang
 */
public class DisabledException extends BadRequestException {

    public DisabledException(String message) {
        super(message);
    }

    public DisabledException(String message, Throwable cause) {
        super(message, cause);
    }
}
