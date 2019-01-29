package me.johnniang.api.exception;

/**
 * My uncaught exception handler for thread.
 *
 * @author johnniang
 */
public class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        throw new BadRequestException(e.getMessage(), e);
    }
}
