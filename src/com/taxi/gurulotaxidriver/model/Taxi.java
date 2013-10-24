/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.taxi.gurulotaxidriver.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;
import com.taxi.gurulotaxidriver.dto.TaxiDTO;

public class Taxi implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("idTaxi")
    private Integer idTaxi;
    
    @SerializedName("name")
    private String name;
    
    @SerializedName("maxSize")
    private Integer maxSize;
    
    @SerializedName("airConditioned")
    private Boolean airConditioned;
    
    @SerializedName("company")
    private String company;

    public Taxi() {
    }

    public Taxi(Integer idTaxi) {
        this.idTaxi = idTaxi;
    }

    public Taxi(String name, Integer maxSize, Boolean airConditioned, String company) {
        this.name = name;
        this.maxSize = maxSize;
        this.airConditioned = airConditioned;
        this.company = company;
    }
    
    public Taxi(TaxiDTO taxiDTO) {
        this.name = taxiDTO.getName();
        this.maxSize = taxiDTO.getMaxSize();
        this.airConditioned = taxiDTO.getAirConditioned();
        this.company = taxiDTO.getCompany();
    }

    public Integer getIdTaxi() {
        return idTaxi;
    }

    public void setIdTaxi(Integer idTaxi) {
        this.idTaxi = idTaxi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(Integer maxSize) {
        this.maxSize = maxSize;
    }

    public Boolean getAirConditioned() {
        return airConditioned;
    }

    public void setAirConditioned(Boolean airConditioned) {
        this.airConditioned = airConditioned;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTaxi != null ? idTaxi.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Taxi)) {
            return false;
        }
        Taxi other = (Taxi) object;
        if ((this.idTaxi == null && other.idTaxi != null) || (this.idTaxi != null && !this.idTaxi.equals(other.idTaxi))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Taxi{" + "idTaxi=" + idTaxi + ", name=" + name + ", maxSize=" + maxSize + ", airConditioned=" + airConditioned + ", company=" + company + '}';
    }
}
