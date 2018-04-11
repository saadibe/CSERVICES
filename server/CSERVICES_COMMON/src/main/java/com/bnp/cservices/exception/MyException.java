package com.bnp.cservices.exception;

import com.bnp.cservices.utils.SerialVersionUID;

/**
 * This exception is meant to be thrown when the application meet an special type of behavior.
 */
public class MyException extends Exception {

    private static final long serialVersionUID = SerialVersionUID.serialVersionUID;


    /**
     * Throws an exception.
     */
    public MyException() {
    }


    /**
     * Throws an exception with a message.
     *
     * @param errorCode the raised error to communicate to the exception.
     */
    public MyException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }


    /**
     * Throws an exception with a message and the content of a previous exception.
     *
     * @param throwable the old exception to communicate.
     */
    public MyException(Throwable throwable) {
        super(throwable);
    }


    /**
     * Throws an exception with a message and the content of a previous exception.
     *
     * @param errorCode the raised error to communicate to the exception.
     * @param throwable the old exception to communicate.
     */
    public MyException(ErrorCode errorCode, Throwable throwable) {
        super(errorCode.getMessage(), throwable);
    }
}
