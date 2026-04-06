package com.example.demo.utils;

public class SendResponse<T> {
    private String status;
    private String message;
    private T data;
    private final int initialPage;
    private final int totalPage;
    
    
    
    public SendResponse(String status, String message, T data){
        this.status = status;
        this.message = message;
        this.data = data;
        this.initialPage = 0;
        this.totalPage = 0;
    }
    
    public String getStatus() {
        return status;
    }
    
    public String getMessage() {
        return message;
    }
    
    public T getData() {
        return data;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public void setData(T data) {
        this.data = data;
    }
    public int getInitialPage(){
        return initialPage;
    }
    public int getTotalPage(){
        return totalPage;
    }
}