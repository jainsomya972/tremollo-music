package com.tremollo.api.Utils;

public class ApiResponse {
    Object body;
    String status;
    String error;

    public ApiResponse(Object body, String status, String error){
        this.body = body;
        this.status = status;
        this.error = error;
    }

    public ApiResponse(Object body, String error){
        this.body = body;
        this.status = "Internal Server Error";
        this.error = error;
    }

    public ApiResponse(Object body){
        this.body = body;
        this.status = "success";
        this.error = "";
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
