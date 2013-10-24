/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.taxi.gurulotaxidriver.util;

import java.util.Map;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author Ezzored
 */
public class JsonResponse {
    private static final float version = 1.0f;  
 
    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private int message;
    @SerializedName("fieldErrors")
    private Map<String, Object> fieldErrors;
    @SerializedName("data")
    private Object data;
 
    public JsonResponse() {
    }
     
    public JsonResponse(String status) {
        this.status = status;
    }    
     
    //@XmlElement //we don't need this thanks to Jackson
    public float getVersion() {
        return JsonResponse.version;
    }
         
    public String getStatus() {
        return status;
    }
 
    public void setStatus(String status) {
        this.status = status;
    }
     
    public int getMessage() {
        return message;
    }
 
    public void setMessage(int errorMsg) {
        this.message = errorMsg;
    }
 
    public Map<String, Object> getFieldErrors() {
        return fieldErrors;
    }
 
    public void setFieldErrors(Map<String, Object> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }
     
    public Object getData() {
        return data;
    }
 
    public void setData(Object data) {
        this.data = data;
    }
}
