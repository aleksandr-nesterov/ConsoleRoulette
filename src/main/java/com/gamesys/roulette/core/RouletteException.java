package com.gamesys.roulette.core;

/**
 * Created by alex on 2/16/16.
 */
public class RouletteException extends RuntimeException {

    public RouletteException() {
    }

    public RouletteException(String message) {
        super(message);
    }

    public RouletteException(String message, Throwable cause) {
        super(message, cause);
    }

    public RouletteException(Throwable cause) {
        super(cause);
    }

    public RouletteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
