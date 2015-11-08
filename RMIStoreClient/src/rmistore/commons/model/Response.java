/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmistore.commons.model;

/**
 *
 * @author dx
 */
public class Response {
    
    private String callbackName;
    private int responseStatus;
    private Object responseArg;
    
    public Response(String callbackName, int responseStatus) {
        this.callbackName = callbackName;
        this.responseStatus = responseStatus;
    }

    public String getCallbackName() {
        return callbackName;
    }

    public void setCallbackName(String callbackName) {
        this.callbackName = callbackName;
    }

    public int getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(int responseStatus) {
        this.responseStatus = responseStatus;
    }

    public Object getResponseArg() {
        return responseArg;
    }

    public void setResponseArg(Object responseArg) {
        this.responseArg = responseArg;
    }
    
}
