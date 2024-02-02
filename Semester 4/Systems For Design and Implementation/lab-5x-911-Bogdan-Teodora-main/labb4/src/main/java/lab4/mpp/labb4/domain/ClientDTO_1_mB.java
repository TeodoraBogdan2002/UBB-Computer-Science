package lab4.mpp.labb4.domain;

import java.util.ArrayList;
import java.util.List;

public class ClientDTO_1_mB {
    private  Long idClient;
    private String FName;
    private String LName;
    private String phoneNR;
    private String email_address;
    private String dateOfBirth;

    private AddressDTO address;

    private List<BookingDTOWithID> bookings= new ArrayList<>();

    public ClientDTO_1_mB() {
    }

    public ClientDTO_1_mB(Long idClient, String FName, String LName, String phoneNR, String email_address, String dateOfBirth) {
        this.idClient = idClient;
        this.FName = FName;
        this.LName = LName;
        this.phoneNR = phoneNR;
        this.email_address = email_address;
        this.dateOfBirth = dateOfBirth;
    }

    public ClientDTO_1_mB(String FName, String LName, String phoneNR, String email_address, String dateOfBirth) {
        this.FName = FName;
        this.LName = LName;
        this.phoneNR = phoneNR;
        this.email_address = email_address;
        this.dateOfBirth = dateOfBirth;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public List<BookingDTOWithID> getBookings() {
        return bookings;
    }

    public void setBookings(List<BookingDTOWithID> bookings) {
        this.bookings = bookings;
    }

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public String getLName() {
        return LName;
    }

    public void setLName(String LName) {
        this.LName = LName;
    }

    public String getPhoneNR() {
        return phoneNR;
    }

    public void setPhoneNR(String phoneNR) {
        this.phoneNR = phoneNR;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
