/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.taxi.gurulotaxidriver.util;

/**
 *
 * @author Ezzored
 */
public class ResponseCodes {
    
    public static final int AUTH_SUCCESS = 1000;
    public static final int AUTH_BAD_PASSWORD = 1001;
    public static final int AUTH_INVALID_USER = 1002;
    public static final int AUTH_INVALID_DRIVER = 1003;
    public static final int AUTH_DRIVER_NO_LICENSE = 1004;
    
    public static final int REG_SUCCESS = 2000;
    public static final int REG_ALREADY_EXIST = 2001;
    public static final int REG_PWD_MISMATCH = 2002;
    public static final int REG_INV_TAXI = 2003;
    
    public static final int INV_PARAM = 3000;
    public static final int ORDER_CREATED = 4000;
    
    public static final int ORDER_ACCEPTED = 4001;
    public static final int ORDER_DECLINED = 4002;
    public static final int ORDER_PENDING = 4003;
    public static final int ORDER_NONE = 4004;
    public static final int ORDER_DELETED = 4005;
    public static final int ORDER_GET_OK = 4006;
    
    public static final int FREETAXI_SAVED = 5000;
    public static final int FREETAXI_DELETED = 5000;

}
