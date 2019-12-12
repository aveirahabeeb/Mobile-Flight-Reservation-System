package model;



public class User {

    private String email_address;
    private String full_name;
    private String address;
    private String country;
    private String phone_number;


    public User(String email_address, String full_name, String address, String country, String phone_number ) {

        this.email_address = email_address;
        this.full_name = full_name;
        this.address = address;
        this.country = country;
        this.phone_number = phone_number;

    }

    public User() {

    }

    public String getEmail_address() {
        return email_address;
    }
    public void setEmail_address(String id) {
        this.email_address = email_address;
    }

    public String getFull_name() {
        return full_name;
    }
    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone_number() {
        return phone_number;
    }
    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }


}
