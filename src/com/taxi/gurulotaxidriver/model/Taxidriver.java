/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.taxi.gurulotaxidriver.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class Taxidriver implements Serializable {

    private static final long serialVersionUID = 1L;

    @SerializedName("idTaxiDriver")
    private Integer idTaxiDriver;

    @SerializedName("phoneNumber")
    private String phoneNumber;

    @SerializedName("password")
    private String password;

    @SerializedName("licensed")
    private Boolean licensed;
    
    @SerializedName("licensenumber")
    private Integer licensenumber;
    
    @SerializedName("taxiidTaxi")
    private Taxi taxiidTaxi;

    public Taxidriver() {
    }

    public Taxidriver(Integer idTaxiDriver) {
        this.idTaxiDriver = idTaxiDriver;
    }

    public Taxidriver(String phoneNumber, String password, Integer licensenumber, Taxi taxiidTaxi) {
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.taxiidTaxi = taxiidTaxi;
        this.licensenumber = licensenumber;
        licensed = true;
    }

    public Integer getIdTaxiDriver() {
        return idTaxiDriver;
    }

    public void setIdTaxiDriver(Integer idTaxiDriver) {
        this.idTaxiDriver = idTaxiDriver;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Taxi getTaxiidTaxi() {
        return taxiidTaxi;
    }

    public void setTaxiidTaxi(Taxi taxiidTaxi) {
        this.taxiidTaxi = taxiidTaxi;
    }

    public Boolean getLicensed() {
        return licensed;
    }

    public void setLicensed(Boolean licensed) {
        this.licensed = licensed;
    }

    public Integer getLicensenumber() {
        return licensenumber;
    }

    public void setLicensenumber(Integer licensenumber) {
        this.licensenumber = licensenumber;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTaxiDriver != null ? idTaxiDriver.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Taxidriver)) {
            return false;
        }
        Taxidriver other = (Taxidriver) object;
        if ((this.idTaxiDriver == null && other.idTaxiDriver != null) || (this.idTaxiDriver != null && !this.idTaxiDriver.equals(other.idTaxiDriver))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Taxidriver{" + "idTaxiDriver=" + idTaxiDriver + ", phoneNumber=" + phoneNumber + ", password=" + password + ", licensed=" + licensed + ", licensenumber=" + licensenumber + ", taxiidTaxi=" + taxiidTaxi + '}';
    }
}
