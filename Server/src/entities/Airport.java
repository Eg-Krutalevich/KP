package entities;

import java.io.Serializable;

public class Airport implements Serializable {

    private String id;
    private String name_airport;
    private String country;
    private String city;

    private String place;

    public Airport(String id, String name_airport, String country, String city, String place) {
        this.id = id;
        this.name_airport = name_airport;
        this.country = country;
        this.city = city;
        this.place = place;
    }

    public String getId() {
        return id;
    }

    public String getName_airport() {
        return name_airport;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName_airport(String name_airport) {
        this.name_airport = name_airport;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
