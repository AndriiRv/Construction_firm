package com.geekhub.finalwork.service.orders.dto;

import java.util.Date;

public class OrderCustomerWorker {
    private Integer id;
    private String titleOfOrder;
    private Integer idWorker;
    private String firstName;
    private String lastName;
    private Integer idCustomer;
    private String titleOfCustomer;
    private Integer idInstrument;
    private String titleOfInstrument;
    private Integer idMaterial;
    private String titleOfMaterial;
    private Date date;
    private String address;
    private Double price;
    private Boolean done;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitleOfOrder() {
        return titleOfOrder;
    }

    public void setTitleOfOrder(String titleOfOrder) {
        this.titleOfOrder = titleOfOrder;
    }

    public Integer getIdWorker() {
        return idWorker;
    }

    public void setIdWorker(Integer idWorker) {
        this.idWorker = idWorker;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(Integer idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getTitleOfCustomer() {
        return titleOfCustomer;
    }

    public void setTitleOfCustomer(String titleOfCustomer) {
        this.titleOfCustomer = titleOfCustomer;
    }

    public Integer getIdInstrument() {
        return idInstrument;
    }

    public void setIdInstrument(Integer idInstrument) {
        this.idInstrument = idInstrument;
    }

    public String getTitleOfInstrument() {
        return titleOfInstrument;
    }

    public void setTitleOfInstrument(String titleOfInstrument) {
        this.titleOfInstrument = titleOfInstrument;
    }

    public Integer getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(Integer idMaterial) {
        this.idMaterial = idMaterial;
    }

    public String getTitleOfMaterial() {
        return titleOfMaterial;
    }

    public void setTitleOfMaterial(String titleOfMaterial) {
        this.titleOfMaterial = titleOfMaterial;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }
}