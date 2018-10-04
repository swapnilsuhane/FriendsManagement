package com.fms.friendsmanagementsystem.exception;

public class FMSException extends Exception{
    private String errorCode;
    private String errorMessage;

    public FMSException() {
        super();
    }

    public FMSException(String message) {
        super(message);
        this.errorMessage = message;
        this.errorCode = "-5000";
    }

    public FMSException(String errorCode,String message) {
        super(message);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        String errMsg = "Something went wrong, FMS Error: " + super.getMessage();
        System.err.println(errMsg);
        return errMsg;
    }
}
