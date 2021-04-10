package com.example.tool;

public class DefinitionException extends RuntimeException {
    private Integer errorCode;
    private String errorMessage;

    public DefinitionException(ResultEnum resultEnum) {
        this.errorCode = resultEnum.getCode();
        this.errorMessage = resultEnum.getMessage();
    }

    public DefinitionException(Integer errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public DefinitionException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
