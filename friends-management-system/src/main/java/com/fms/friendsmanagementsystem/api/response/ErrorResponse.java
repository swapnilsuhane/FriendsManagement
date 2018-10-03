package com.fms.friendsmanagementsystem.api.response;

public class ErrorResponse extends BaseResponse{
    private String errorCode;
    private String errorMessage;

    public ErrorResponse() {
        super.success = false;
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
}
