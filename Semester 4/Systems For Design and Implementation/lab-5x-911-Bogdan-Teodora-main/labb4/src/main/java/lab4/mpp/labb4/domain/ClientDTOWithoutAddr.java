package lab4.mpp.labb4.domain;

public class ClientDTOWithoutAddr {
    private  Long idClient;
    private String FName;
    private String LName;
    private String phoneNR;
    private String email_address;
    private String dateOfBirth;

    public ClientDTOWithoutAddr() {
    }

    public ClientDTOWithoutAddr(Long idClient, String FName, String LName, String phoneNR, String email_address, String dateOfBirth) {
        this.idClient = idClient;
        this.FName = FName;
        this.LName = LName;
        this.phoneNR = phoneNR;
        this.email_address = email_address;
        this.dateOfBirth = dateOfBirth;
    }

    public ClientDTOWithoutAddr(String FName, String LName, String phoneNR, String email_address, String dateOfBirth) {
        this.FName = FName;
        this.LName = LName;
        this.phoneNR = phoneNR;
        this.email_address = email_address;
        this.dateOfBirth = dateOfBirth;
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
