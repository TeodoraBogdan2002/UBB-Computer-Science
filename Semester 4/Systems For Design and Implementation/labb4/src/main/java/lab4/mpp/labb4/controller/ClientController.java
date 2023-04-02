package lab4.mpp.labb4.controller;

import jakarta.validation.Valid;
import lab4.mpp.labb4.app.ClientNotFoundException;
import lab4.mpp.labb4.domain.*;
import lab4.mpp.labb4.repo.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


@RestController
class ClientController {

    @Autowired
    private final ClientRepository repository;
    private final BookingRepository bookingRepo;
    private final AddressRepository addrRepo;

    ClientController(ClientRepository repository, BookingRepository bookingRepo, AddressRepository addrRepo) {
        this.repository = repository;
        this.bookingRepo = bookingRepo;
        this.addrRepo = addrRepo;
    }

    // Aggregate root
    // tag::get-aggregate-root[]

//    List<Client> all() {
//        return repository.findAll();
//    }
    @GetMapping("/clients")
    List<ClientDTO> all() {
        ModelMapper modelMapper = new ModelMapper();
        List<Client> clients = repository.findAll();

        modelMapper.typeMap(Client.class,ClientDTO.class).addMapping(client -> client.getAddress().getAddress_id(),ClientDTO::setAddressID);
        return clients.stream().map(client -> modelMapper.map(client,ClientDTO.class)).collect(Collectors.toList());
//        ModelMapper modelMapper = new ModelMapper();
//        List<Client> clients = repository.findAll();
//        List<ClientDTO> clientDTOS = clients.stream()
//                .map(client -> modelMapper.map(client, ClientDTO.class))
//                .collect(Collectors.toList());
//        return clientDTOS;
    }
    // end::get-aggregate-root[]

    @PostMapping("/clients/{addressId}")
    Client newClient(@Valid @RequestBody Client newClient,@PathVariable Long addressId) {
//        return repository.save(newClient);
        Address address=addrRepo.findById(addressId).get();
        newClient.setAddress(address);
        //newAdoption.getAdoptionCustomers().isEmpty();
        newClient=repository.save(newClient);
        return newClient;
    }

//    @PostMapping("/clients/{clientId}/bookings")
//    BookingDetails newBookingForClient(@PathVariable Long clientId, @RequestBody BookingDetails newBooking) {
//        // Find the client by ID
//        Client client = repository.findById(clientId)
//                .orElseThrow(() -> new ClientNotFoundException(clientId));
//
//        // Check if the booking with the given ID already exists
//        BookingDetails existingBooking = null;
//        for (BookingDetails booking : bookingRepo.findAll()) {
//            if (booking.equals(newBooking)) {
//                existingBooking = booking;
//                break;
//            }
//        }
//        if (existingBooking != null) {
//            // If the booking already exists, set the client property and return the existing booking
//            existingBooking.setClient(client);
//            bookingRepo.save(existingBooking);
//            return existingBooking;
//        } else {
//            // If the booking does not exist, set the client property for the new booking
//            newBooking.setClient(client);
//            // Save the new booking to the database
//            BookingDetails savedBooking = bookingRepo.save(newBooking);
//            // Add the new booking to the client
//            client.getBookingDetailsSet().add(savedBooking);
//            // Save the client to update the bookings list
//            repository.save(client);
//            return savedBooking;
//        }
//    }

    // Single item

//    @GetMapping("/clients/{id}")
//    Client one(@PathVariable Long id) {
//        return repository.findById(id)
//                .orElseThrow(() -> new ClientNotFoundException(id));
//    }
    @GetMapping("/clients/{id}")
    ClientDTOWithCarsIds one (@PathVariable Long id){
        if (repository.findById(id).isEmpty())
            throw new ClientNotFoundException(id);

        Client client=repository.findById(id).get();
        ClientDTOWithCarsIds clientDTOWithCarIds=new ClientDTOWithCarsIds();

        List<Long> carsIds=new ArrayList<>();
        List<BookingDetails> bookingDetails=bookingRepo.findAll();
        for(BookingDetails ac:bookingDetails)
            if(Objects.equals(ac.getClient().getId(), client.getId()))
                carsIds.add(ac.getCar().getId());
        clientDTOWithCarIds.setCarsIds(carsIds);
        clientDTOWithCarIds.setClient(client);
        clientDTOWithCarIds.setAddressId(client.getAddress().getAddress_id());

        return  clientDTOWithCarIds;
//        if(repository.findById(id).isEmpty())
//            throw new ClientNotFoundException(id);
//        ModelMapper modelMapper=new ModelMapper();
//        modelMapper.typeMap(Client.class,ClientDTO_1_mB.class).addMapping(client -> {
//            List<BookingDetails> bookings = client.getBookingDetailsSet();
//            if(bookings!=null){
//                return bookings.stream().map(bookingDetails -> modelMapper.map(bookingDetails, BookingDTOWithID.class)).collect(Collectors.toList());
//            }
//            else{
//                return Collections.emptyList();
//            }
//        },ClientDTO_1_mB::setBookings);
//        ClientDTO_1_mB clientDTO1MB = modelMapper.map(repository.findById(id).get(),ClientDTO_1_mB.class);
//        return clientDTO1MB;
    }



    @PutMapping("/clients/{id}")
    Client replaceClient(@RequestBody Client newClient, @PathVariable Long id) {

        return repository.findById(id)
                .map(client -> {
                    client.setFName(newClient.getFName());
                    client.setLName(newClient.getLName());
                    client.setPhoneNR(newClient.getPhoneNR());
                    client.setEmail_address(newClient.getEmail_address());
                    client.setDateOfBirth(newClient.getDateOfBirth());
                    return repository.save(client);
                })
                .orElseGet(() -> {
                    newClient.setId(id);
                    return repository.save(newClient);
                });
    }

    @DeleteMapping("/clients/{id}")
    void deleteClient(@PathVariable Long id) {
        repository.deleteById(id);
    }

    //statistics: show the clients orderd by the average of the number of kilometers of their booked/reserved cars
    @GetMapping("/clients/statistics")
    public List<ClientsDTOStatisticsCars> getClientsStatistics() {
        List<ClientsDTOStatisticsCars> clientsStatistics = new ArrayList<>();
        List<Client> clients = repository.findAll();
        ModelMapper modelMapper = new ModelMapper();
        for (Client client : clients) {
            double sum = 0.0;
            int count = 0;
            for (BookingDetails booking : client.getBookingDetailsSet()) {
                sum += booking.getCar().getNrkilometers();
                count++;
            }
            double avgNrkilometers = count > 0 ? sum / count : 0.0;
            avgNrkilometers = Math.round(avgNrkilometers * 100.0) / 100.0;
            ClientsDTOStatisticsCars clientDTOStatisticsCars = modelMapper.map(client, ClientsDTOStatisticsCars.class);
            clientDTOStatisticsCars.setAvgCarNrKilometers(avgNrkilometers);
            clientsStatistics.add(clientDTOStatisticsCars);
        }
        clientsStatistics.sort(Comparator.comparingDouble(ClientsDTOStatisticsCars::getAvgCarNrKilometers).reversed());
        return clientsStatistics;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex)
    {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName =((FieldError) error).getField();
            String errorMessage =error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}

