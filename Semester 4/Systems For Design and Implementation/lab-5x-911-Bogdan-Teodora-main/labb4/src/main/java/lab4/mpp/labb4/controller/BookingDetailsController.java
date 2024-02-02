package lab4.mpp.labb4.controller;

import lab4.mpp.labb4.app.BookingNotFoundException;
import lab4.mpp.labb4.domain.*;
import lab4.mpp.labb4.repo.*;
import lab4.mpp.labb4.service.BookingDetailsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
class BookingDetailsController {

    private final BookingDetailsService service;
//    @Autowired
//    private final BookingRepository repository;
//    private final ClientRepository clientRepository;
//    private final CarRepository carRepository;
//
//    public BookingDetailsController(BookingRepository repository, ClientRepository clientRepository, CarRepository carRepository) {
//        this.repository = repository;
//        this.clientRepository = clientRepository;
//        this.carRepository = carRepository;
//    }


    public BookingDetailsController(BookingDetailsService service) {
        this.service = service;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/bookings")
    List<BookingDTOWithID> all() {
        return service.all();
    }

    @GetMapping("/bookings/paged")
    public List<BookingDTOWithID> AllPaged(
            @RequestParam int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        PageRequest pr = PageRequest.of(page, size);
        return service.allPaged(pr);
    }
    // end::get-aggregate-root[]

    @PostMapping("/bookings/{clientId}/{carId}")
    BookingDetails newBooking(@RequestBody BookingDetails newBooking, @PathVariable Long clientId, @PathVariable Long carId) {
        return service.newBooking(newBooking,clientId,carId);
    }

    @PostMapping("/bookings/{carId}")
    List<BookingDetails> newClientCarList(@RequestBody List<BookingDTOWithID> BookingDetList,@PathVariable Long carId) {
        return service.newClientCarList(BookingDetList,carId);
    }

    @PostMapping("/bookings/add")
    BookingDetails addBooking(@RequestBody BookingDTOWithID newB) {
        return service.addBooking(newB);
    }

    // Single item

    @GetMapping("/bookings/{id}")
    BookingDTO one(@PathVariable Long id) {
        return service.one(id);
    }

    @GetMapping("/bookings/{id}/details")
    BookingDTO oneBooking(@PathVariable String id) {
        Long clientId = Long.parseLong(id);

        return service.oneBooking(clientId);
    }

    @PutMapping("/bookings/{id}/edit")
    BookingDetails replaceBooking(@RequestBody BookingDetails newBooking, @PathVariable Long id) {
        return service.replaceBooking(newBooking,id);
    }

    @DeleteMapping("/bookings/{id}/delete")
    void deleteBooking(@PathVariable Long id) {
        service.deleteBooking(id);
    }

    @GetMapping("/bookings/amount/{minAmount}")
    List<BookingDetails> byAmount(@PathVariable int minAmount) {
        return service.byAmount(minAmount);
    }

    @GetMapping("/bookings/countAll")
    public Long countAllBookings()
    {
        return this.service.countAllBookings();
    }
}

