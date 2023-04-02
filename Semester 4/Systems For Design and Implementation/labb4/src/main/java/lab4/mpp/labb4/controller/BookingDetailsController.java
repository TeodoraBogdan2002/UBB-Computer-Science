package lab4.mpp.labb4.controller;

import lab4.mpp.labb4.app.BookingNotFoundException;
import lab4.mpp.labb4.domain.*;
import lab4.mpp.labb4.repo.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
class BookingDetailsController {

    @Autowired
    private final BookingRepository repository;
    private final ClientRepository clientRepository;
    private final CarRepository carRepository;

    public BookingDetailsController(BookingRepository repository, ClientRepository clientRepository, CarRepository carRepository) {
        this.repository = repository;
        this.clientRepository = clientRepository;
        this.carRepository = carRepository;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/bookings")
    List<BookingDTOWithID> all() {
//        return repository.findAll();
//        ModelMapper modelMapper= new ModelMapper();
//        modelMapper.typeMap(BookingDetails.class, BookingDTOWithID.class);
//        List<BookingDTOWithID> bookingDTOWithIDS = repository.findAll().stream()
//                .map(booking -> modelMapper.map(booking, BookingDTOWithID.class))
//                .collect(Collectors.toList());
//        return bookingDTOWithIDS;
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(BookingDetails.class, BookingDTOWithID.class);

        List<BookingDetails> bookingDetails = repository.findAll();
        return bookingDetails.stream()
                .map(adoptionCustomer -> modelMapper.map(adoptionCustomer, BookingDTOWithID.class))
                .collect(Collectors.toList());
    }
    // end::get-aggregate-root[]

    @PostMapping("/bookings/{clientId}/{carId}")
    BookingDetails newBooking(@RequestBody BookingDetails newBooking, @PathVariable Long clientId, @PathVariable Long carId) {
        //return repository.save(newBooking);
        Client client = clientRepository.findById(clientId).get();
        Car car = carRepository.findById(carId).get();
        newBooking.setClient(client);
        newBooking.setCar(car);
        newBooking=repository.save(newBooking);

        client.getBookingDetailsSet().add(newBooking);
        car.getBookingDetailsSet().add(newBooking);
        return newBooking;
    }

    @PostMapping("/bookings/{carId}")
    List<BookingDetails> newClientCarList(@RequestBody List<BookingDTOWithID> BookingDetList,@PathVariable Long carId) {
        Car selectedCar=carRepository.findById(carId).get();
        List<BookingDetails> finalLits= new ArrayList<>();
        for(BookingDTOWithID bddto:BookingDetList){
            BookingDetails ac=new BookingDetails();
            ac.setAmount(bddto.getAmount());
            ac.setDrop_loc(bddto.getDrop_loc());
            ac.setPickup_loc(bddto.getPickup_loc());
            ac.setStartDate(bddto.getStartDate());
            ac.setReturnDate(bddto.getReturnDate());
            ac.setBookingStatus(bddto.getBookingStatus());
            ac.setCar(selectedCar);
            Client selectedClient=clientRepository.findById(bddto.getClientId()).get();
            ac.setClient(selectedClient);
            ac=repository.save(ac);
            finalLits.add(ac);
        }
        return finalLits;
    }

    // Single item

    @GetMapping("/bookings/{id}")
    BookingDTO one(@PathVariable Long id) {

        //return repository.findByIdBooking(id);
//        if (repository.findById(id).isEmpty())
//            throw new BookingNotFoundException(id);
//
//        ModelMapper modelMapper = new ModelMapper();
//        BookingDTO bookingDTO = modelMapper.map(repository.findById(id).get(), BookingDTO.class);
//        return bookingDTO;
        if (repository.findById(id).isEmpty())
            throw new BookingNotFoundException(id);

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(BookingDetails.class, BookingDTO.class);

        BookingDetails adoptionCustomer=repository.findById(id).get();
        return  modelMapper.map(adoptionCustomer, BookingDTO.class);
    }

    @PutMapping("/bookings/{id}")
    BookingDetails replaceBooking(@RequestBody BookingDetails newBooking, @PathVariable Long id) {

        return repository.findById(id)
                .map(bookingDetails -> {
                    bookingDetails.setStartDate(newBooking.getStartDate());
                    bookingDetails.setReturnDate(newBooking.getReturnDate());
                    bookingDetails.setAmount(newBooking.getAmount());
                    bookingDetails.setBookingStatus(newBooking.getBookingStatus());
                    bookingDetails.setDrop_loc(newBooking.getDrop_loc());
                    bookingDetails.setPickup_loc(newBooking.getPickup_loc());
//                    bookingDetails.setClient(newBooking.getClient());
                    return repository.save(bookingDetails);
                })
                .orElseGet(() -> {
                    newBooking.setId(id);
                    return repository.save(newBooking);
                });
    }

    @DeleteMapping("/bookings/{id}")
    void deleteBooking(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @GetMapping("/bookings/amount/{minAmount}")
    List<BookingDetails> byAmount(@PathVariable int minAmount) {
        return repository.findByAmountGreaterThanEqual(minAmount);
    }
}

