package lab4.mpp.labb4.controller;

import lab4.mpp.labb4.app.AddressNotFoundException;
import lab4.mpp.labb4.domain.Address;
import lab4.mpp.labb4.domain.AddressDTO;
import lab4.mpp.labb4.domain.CarDTO;
import lab4.mpp.labb4.repo.AddressRepository;
import lab4.mpp.labb4.repo.ClientRepository;
import lab4.mpp.labb4.service.AddressService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
//@Service
class AddressController {
    private final AddressService addressService;
//    private final AddressRepository repository;
//    private ClientRepository clientrepo;
//
//    public AddressController(AddressRepository repository, ClientRepository clientrepo) {
//        this.repository = repository;
//        this.clientrepo = clientrepo;
//    }


    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/addresses")
    List<AddressDTO> all() {
        return addressService.all();
    }
    // end::get-aggregate-root[]

    @PostMapping("/addresses/add")
    Address newAddress(@RequestBody Address newAddress) {
        return addressService.newAddress(newAddress);
    }

    @GetMapping("/addresses/paged")
    public List<AddressDTO> AllPaged(
            @RequestParam(value = "page", defaultValue = "1", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        PageRequest pr = PageRequest.of(page, size);
        return addressService.allPaged(pr);
    }
    // Single item

    @GetMapping("/addresses/{id}/details")
    Address one(@PathVariable Long id) {
        return addressService.one(id);
    }

    @PutMapping("/addresses/{id}/edit")
    Address replaceAddress(@RequestBody Address newAddress, @PathVariable Long id) {
        return addressService.replaceAddress(newAddress,id);
    }

    @DeleteMapping("/addresses/{id}/delete")
    void deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
    }

    @GetMapping("/addresses/countAll")
    public Long countAllAddresses()
    {
        return this.addressService.countAllAddresses();
    }

    @GetMapping("/addresses/autocomplete")
    public List<Address> getAddressesSuggestions(@RequestParam String query)
    {
        return this.addressService.getAddressIdsAutocomplete(query);
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


//    @GetMapping("/cars/nrKilometers/{minNrKilometers}")
//    List<Car> byNrKilometers(@PathVariable int minNrKilometers) {
//        return repository.findByNrkilometersGreaterThanEqual(minNrKilometers);
//    }
}

