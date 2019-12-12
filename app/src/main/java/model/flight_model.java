package model;


import java.io.Serializable;

public class flight_model implements Serializable {

    private String flight_id;
    private String flight_name;
    private String flight_source;
    private String flight_destination;
    private String departure;
    private String arrival;
    private String takeoff_date;
    private String flight_price;
    private String available_seat;

    public flight_model(String flight_id, String flight_name, String flight_source, String flight_price, String flight_destination, String departure, String arrival, String takeoff_date, String available_seat) {
        this.flight_id = flight_id;
        this.flight_name = flight_name;
        this.flight_source = flight_source;
        this.flight_price = flight_price;
        this.flight_destination = flight_destination;
        this.departure = departure;
        this.arrival = arrival;
        this.takeoff_date = takeoff_date;
        this.available_seat = available_seat;
    }


    public flight_model() {

    }

    public String getFlight_id() {
        return flight_id;
    }

    public void setFlight_id(String flight_id) {
        this.flight_id = flight_id;
    }

    public String getFlight_name() {
        return flight_name;
    }

    public void setFlight_name(String flight_name) {
        this.flight_name = flight_name;
    }

    public String getFlight_source() {
        return flight_source;
    }

    public void setFlight_source(String flight_source) {
        this.flight_source = flight_source;
    }

    public String getFlight_destination() {
        return flight_destination;
    }

    public void setFlight_destination(String flight_destination) {
        this.flight_destination = flight_destination;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getTakeoff_date() {
        return takeoff_date;
    }

    public void setTakeoff_date(String takeoff_date) {
        this.takeoff_date = takeoff_date;
    }

    public String getFlight_price() {
        return flight_price;
    }

    public void setFlight_price(String flight_price) {
        this.flight_price = flight_price;
    }

    public String getAvailable_seat() {
        return available_seat;
    }

    public void setAvailable_seat(String available_seat) {
        this.available_seat = available_seat;
    }



}
