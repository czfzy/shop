package com.example.tool;

public class HttpResult<T> {
    private Integer code; //响应码
    private String message; //响应信息
    private T data; //数据

    public HttpResult() {
    }

    public HttpResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public HttpResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public HttpResult(ResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
    }

    public HttpResult(ResultEnum resultEnum, T data) {
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
        this.data = data;
    }

    public static HttpResult<Object> defineError(DefinitionException definitionException) {
        return new HttpResult<>(definitionException.getErrorCode(), definitionException.getErrorMessage());
    }

    public static HttpResult<Object> otherError(ResultEnum resultEnum) {
        return new HttpResult<>(resultEnum);
    }

    public static HttpResult<Object> success(Object data) {
        return new HttpResult<>(ResultEnum.SUCCESS, data);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
