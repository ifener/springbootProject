package com.wey.springboot.websocket;

public class SocketResponse {
    
    public SocketResponse(String msg) {
        this.responseMessage = msg;
    }
    
    private String responseMessage;
    
    public String getResponseMessage() {
        return responseMessage;
    }
    
    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
    
}
