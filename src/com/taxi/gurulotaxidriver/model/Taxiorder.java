/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.taxi.gurulotaxidriver.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class Taxiorder implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("idOrder")
    private Integer idOrder;
    
    @SerializedName("location")
    private String location;
    
    @SerializedName("accepted")
    private Boolean accepted;
    
    @SerializedName("taxidriveridTaxiDriver")
    private Taxidriver taxidriveridTaxiDriver;
    
    @SerializedName("clientuseridClientUser")
    private Clientuser clientuseridClientUser;

    public Taxiorder() {
    }

    public Taxiorder(Integer idOrder) {
        this.idOrder = idOrder;
    }

    public Taxiorder(String location, Taxidriver taxidriveridTaxiDriver, Clientuser clientuseridClientUser) {
        this.location = location;
        this.taxidriveridTaxiDriver = taxidriveridTaxiDriver;
        this.clientuseridClientUser = clientuseridClientUser;
        this.accepted=false;
    }

    public Integer getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Integer idOrder) {
        this.idOrder = idOrder;
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

    public Clientuser getClientuseridClientUser() {
        return clientuseridClientUser;
    }

    public void setClientuseridClientUser(Clientuser clientuseridClientUser) {
        this.clientuseridClientUser = clientuseridClientUser;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOrder != null ? idOrder.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Taxiorder)) {
            return false;
        }
        Taxiorder other = (Taxiorder) object;
        if ((this.idOrder == null && other.idOrder != null) || (this.idOrder != null && !this.idOrder.equals(other.idOrder))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Taxiorder{" + "idOrder=" + idOrder + ", location=" + location + ", taxidriveridTaxiDriver=" + taxidriveridTaxiDriver + ", clientuseridClientUser=" + clientuseridClientUser + '}';
    }
}
