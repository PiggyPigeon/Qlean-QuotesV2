package org.launchcode.qleanquotes.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import org.launchcode.qleanquotes.models.enums.CleaningOption;

@Entity
public class Quote extends AbstractEntity {

    @OneToOne
    @JoinColumn(name = "orders_id", referencedColumnName = "id")
    private Orders orders;

    private Integer squareFeet;

    private Integer numOfRoom;

    private Integer numOfBathroom;

    private CleaningOption cleaningOption;

    private Long totalCharge;

    private double totalCost;
    private String formattedTotalCost;

    public Quote() {
    }

    public Quote(Orders anOrders, Integer squareFeet, Integer numOfRoom, Integer numOfBathroom, CleaningOption cleaningOption, Long totalCharge, double totalCost, String formattedTotalCost) {
        super();
        this.orders = anOrders;
        this.squareFeet = squareFeet;
        this.numOfRoom = numOfRoom;
        this.numOfBathroom = numOfBathroom;
        this.cleaningOption = cleaningOption;
        this.totalCharge = totalCharge;
        this.totalCost = totalCost;
        this.formattedTotalCost = formattedTotalCost;
    }

    public Integer getSquareFeet() {
        return squareFeet;
    }

    public void setSquareFeet(Integer squareFeet) {
        this.squareFeet = squareFeet;
    }

    public Integer getNumOfRoom() {
        return numOfRoom;
    }

    public void setNumOfRoom(Integer numOfRoom) {
        this.numOfRoom = numOfRoom;
    }

    public Integer getNumOfBathroom() {
        return numOfBathroom;
    }

    public void setNumOfBathroom(Integer numOfBathroom) {
        this.numOfBathroom = numOfBathroom;
    }

    public CleaningOption getCleaningOption() {
        return cleaningOption;
    }

    public void setCleaningOption(CleaningOption cleaningOption) {
        this.cleaningOption = cleaningOption;
    }

    public Long getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(Long totalCharge) {
        this.totalCharge = totalCharge;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getFormattedTotalCost() {
        return formattedTotalCost;
    }

    public void setFormattedTotalCost(String formattedTotalCost) {
        this.formattedTotalCost = formattedTotalCost;
    }

    public Orders getOrder() {
        return orders;
    }

    public void setOrder(Orders orders) {
        this.orders = orders;
    }
}
