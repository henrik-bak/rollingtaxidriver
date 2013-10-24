/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.taxi.gurulotaxidriver.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class Freetaxi implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("idfreetaxi")
    private Integer idfreetaxi;
    
    @SerializedName("location")
    private String location;

    @SerializedName("taxidriveridTaxiDriver")
    private Taxidriver taxidriveridTaxiDriver;

    public Freetaxi() {
    }

    public Freetaxi(Integer idfreetaxi) {
        this.idfreetaxi = idfreetaxi;
    }

    public Freetaxi(String location, Taxidriver taxidriveridTaxiDriver) {
        this.location = location;
        this.taxidriveridTaxiDriver = taxidriveridTaxiDriver;
    }

    public Integer getIdfreetaxi() {
        return idfreetaxi;
    }

    public void setIdfreetaxi(Integer idfreetaxi) {
        this.idfreetaxi = idfreetaxi;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Taxidriver getTaxidriveridTaxiDriver() {
        return taxidriveridTaxiDriver;
    }

    public void setTaxidriveridTaxiDriver(Taxidriver taxidriveridTaxiDriver) {
        this.taxidriveridTaxiDriver = taxidriveridTaxiDriver;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idfreetaxi != null ? idfreetaxi.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Freetaxi)) {
            return false;
        }
        Freetaxi other = (Freetaxi) object;
        if ((this.idfreetaxi == null && other.idfreetaxi != null) || (this.idfreetaxi != null && !this.idfreetaxi.equals(other.idfreetaxi))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Freetaxi{" + "idfreetaxi=" + idfreetaxi + ", location=" + location + ", taxidriveridTaxiDriver=" + taxidriveridTaxiDriver + '}';
    }
    
}
