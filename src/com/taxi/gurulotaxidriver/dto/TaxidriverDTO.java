/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.taxi.gurulotaxidriver.dto;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author Ezzored
 */
public class TaxidriverDTO {

	@SerializedName("licensenumber")
    private Integer licensenumber;
	@SerializedName("phoneNumber")
    private String phoneNumber;
	@SerializedName("password1")
    private String password1;
	@SerializedName("password2")
    private String password2;
	@SerializedName("taxi")
    private TaxiDTO taxi;

    public TaxidriverDTO() {
    }

    public TaxidriverDTO(String phoneNumber, String password1, String password2, Integer licensenumber, TaxiDTO taxi) {
        this.phoneNumber = phoneNumber;
        this.password1 = password1;
        this.password2 = password2;
        this.licensenumber = licensenumber;
        this.taxi=taxi;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public TaxiDTO getTaxi() {
        return taxi;
    }

    public void setTaxi(TaxiDTO taxi) {
        this.taxi = taxi;
    }

    public Integer getLicensenumber() {
        return licensenumber;
    }

    public void setLicensenumber(Integer licensenumber) {
        this.licensenumber = licensenumber;
    }

    @Override
    public String toString() {
        return "TaxidriverDTO{" + "licensenumber=" + licensenumber + ", phoneNumber=" + phoneNumber + ", password1=" + password1 + ", password2=" + password2 + ", taxi=" + taxi + '}';
    }

}
