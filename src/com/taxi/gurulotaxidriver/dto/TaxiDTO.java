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
public class TaxiDTO {
    
	@SerializedName("name")
    private String name;
    
	@SerializedName("maxSize")
    private Integer maxSize;
    
	@SerializedName("airConditioned")
    private Boolean airConditioned;
    
	@SerializedName("company")
    private String company;

    public TaxiDTO() {
    }

    public TaxiDTO(String name, Integer maxSize, Boolean airConditioned, String company) {
        this.name = name;
        this.maxSize = maxSize;
        this.airConditioned = airConditioned;
        this.company = company;
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
    public String toString() {
        return "TaxiDTO{" + "name=" + name + ", maxSize=" + maxSize + ", airConditioned=" + airConditioned + ", company=" + company + '}';
    }   
}
