package com.wr.hadoop.service.storm;

/**
 * Created by Administrator on 2016/8/27.
 */
public class StoreInfo {
    private String name;
    private String starType;
    private Float tasteGrade;
    private Float atomsGrade;
    private Float serviceGrade;
    private String address;
    private String telphone;

    public StoreInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStarType() {
        return starType;
    }

    public void setStarType(String starType) {
        this.starType = starType;
    }

    public Float getTasteGrade() {
        return tasteGrade;
    }

    public void setTasteGrade(Float tasteGrade) {
        this.tasteGrade = tasteGrade;
    }

    public Float getAtomsGrade() {
        return atomsGrade;
    }

    public void setAtomsGrade(Float atomsGrade) {
        this.atomsGrade = atomsGrade;
    }

    public Float getServiceGrade() {
        return serviceGrade;
    }

    public void setServiceGrade(Float serviceGrade) {
        this.serviceGrade = serviceGrade;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }
}
