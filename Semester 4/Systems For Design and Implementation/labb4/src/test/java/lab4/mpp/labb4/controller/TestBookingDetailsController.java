package lab4.mpp.labb4.controller;

import lab4.mpp.labb4.domain.BookingDetails;
import lab4.mpp.labb4.repo.BookingRepository;
import lab4.mpp.labb4.repo.CarRepository;
import lab4.mpp.labb4.repo.ClientRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class TestBookingDetailsController {
    @Mock
    private BookingRepository bookingRepository;

    private BookingDetailsController bookingDetailsController;
    private ClientRepository clientRepository;
    private CarRepository carRepository;

    @Before
    public void setUp() {

        bookingDetailsController = new BookingDetailsController(bookingRepository, clientRepository, carRepository);
    }
    @Test
    public void testPetControllerFilterByPrice() throws Exception {
        // Create a list of pets with prices greater than or equal to the minimum price
        BookingDetails bookingDetails1 = new BookingDetails(1L,"12 Feb","12 Mar",1000,"booked","cluj","cluj");
        BookingDetails bookingDetails2 = new BookingDetails(2L,"20 Jul","14 Aug",2000,"reserved","brasov","cluj");
        List<BookingDetails> bookings = Arrays.asList(bookingDetails1, bookingDetails2);
        List<BookingDetails> expensiveBooking = Arrays.asList(bookingDetails2);


        // Mock the behavior of the petRepository to return the list of pets when findByPriceGreaterThanEqual is called
        when(bookingRepository.findByAmountGreaterThanEqual(900)).thenReturn(bookings);

//        // Call the byPrice method with a minimum price of 100 and verify that it returns the expected list of pets
//        List<BookingDetails> result = bookingDetailsController.byAmount(900);
//        assertEquals(bookings, result);
//
//        // Verify that findByPriceGreaterThanEqual was called on the petRepository with the correct argument
//        verify(bookingRepository, times(1)).findByAmountGreaterThanEqual(900);
        MockMvc mockMvc = standaloneSetup(bookingDetailsController).build();
        mockMvc.perform(get("/bookings/amount/900"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].drop_loc").value("cluj"))
                .andExpect(jsonPath("$[0].amount").value(1000))
                .andExpect(jsonPath("$[1].bookingStatus").value("reserved"))
                .andExpect(jsonPath("$[1].amount").value(2000));


        when(bookingRepository.findByAmountGreaterThanEqual(1500)).thenReturn(expensiveBooking);

        MockMvc mockMvc2 = standaloneSetup(bookingDetailsController).build();
        mockMvc2.perform(get("/bookings/amount/1500"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bookingStatus").value("reserved"))
                .andExpect(jsonPath("$[0].amount").value(2000));
    }
}
