package entities;

import java.io.Serializable;

public class Flight implements Serializable {
    private String airport;
    private String date;
    private String outTime;
    private String inTime;
    private String seatsAmount;
    private String price;
    private String id;

    public Flight(String id, String airport, String date, String outTime, String inTime, String seatsAmount, String price) {
        this.airport = airport;
        this.date = date;
        this.outTime = outTime;
        this.inTime = inTime;
        this.seatsAmount = seatsAmount;
        this.price = price;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String getSeatsAmount() {
        return seatsAmount;
    }

    public void setSeatsAmount(String seatsAmount) {
        this.seatsAmount = seatsAmount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
