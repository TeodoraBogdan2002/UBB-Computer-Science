package lab4.mpp.labb4.domain;

import java.util.List;

public class CarDTOWithClients {
    Car car;
    private List<Long> clientsIds;

    public CarDTOWithClients() {
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car client) {
        this.car = client;
    }

    public List<Long> getClientsIds() {
        return clientsIds;
    }

    public void setClientsIds(List<Long> clientsIds) {
        this.clientsIds = clientsIds;
    }
}
