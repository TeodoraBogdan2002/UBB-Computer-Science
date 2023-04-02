package lab4.mpp.labb4.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CarDTO {
    private  Long id;
    private String model;
    private String brand;
    private String color;
    private int year_manufacture;
    private int nrkilometers;
    //private List<BookingDTOWithID> bookings= new ArrayList<>();

    public CarDTO() {
    }

    public CarDTO(Long id, String model, String brand, String color, int year_manufacture, int nrkilometers) {
        this.id = id;
        this.model = model;
        this.brand = brand;
        this.color = color;
        this.year_manufacture = year_manufacture;
        this.nrkilometers = nrkilometers;
    }

    public CarDTO(String model, String brand, String color, int year_manufacture, int nrkilometers) {
        this.model = model;
        this.brand = brand;
        this.color = color;
        this.year_manufacture = year_manufacture;
        this.nrkilometers = nrkilometers;
    }

//    public List<BookingDTOWithID> getBookings() {
//        return bookings;
//    }

//    public void setBookings(List<BookingDTOWithID> bookings) {
//        this.bookings = bookings;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getYear_manufacture() {
        return year_manufacture;
    }

    public void setYear_manufacture(int year_manufacture) {
        this.year_manufacture = year_manufacture;
    }

    public int getNrkilometers() {
        return nrkilometers;
    }

    public void setNrkilometers(int nrkilometers) {
        this.nrkilometers = nrkilometers;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof Car car)) return false;
//        return getId().equals(car.getId()) && getModel().equals(car.getModel()) &&  getBrand().equals(car.getBrand()) && getColor().equals(car.getColor());//getPetId().equals(adoption.getPetId()) && getCustomerId().equals(adoption.getCustomerId()) &&
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(getId(), getModel(), getBrand(), getColor(), getYear_manufacture(), getNrkilometers());
//    }
}
/*
 id, model, brand, color, year_manufacture, nrkilometers;
 */
