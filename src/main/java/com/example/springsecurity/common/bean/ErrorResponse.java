package com.example.springsecurity.common.bean;

/**
 *  SpringSecurity服务执行异常返会model
 * @author tanchong
 * Create Date: 2020/2/20
 */
public class ErrorResponse {

    private String error;

    public ErrorResponse(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "{" +
                "error='" + error + '\'' +
                '}';
    }
}
