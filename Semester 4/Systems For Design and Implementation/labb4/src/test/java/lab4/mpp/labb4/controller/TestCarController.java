package lab4.mpp.labb4.controller;

import static org.junit.Assert.assertEquals;

import lab4.mpp.labb4.domain.BookingDetails;
import lab4.mpp.labb4.domain.Car;
import lab4.mpp.labb4.domain.CarsDTOStatisticsBookingPrice;
import lab4.mpp.labb4.repo.BookingRepository;
import lab4.mpp.labb4.repo.CarRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestCarController {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private CarRepository carRepository;

    @MockBean
    private BookingRepository bookingRepository;

    @Test
    public void testGetAllAdoptionsOrderByAvgPetPrice() {
        // Create test data
        BookingDetails bookingDetails1 = new BookingDetails(1L,"12 Feb","12 Mar",1000,"booked","cluj","cluj");
        BookingDetails bookingDetails2 = new BookingDetails(2L,"20 Jul","14 Aug",2000,"reserved","brasov","cluj");
        Car car1 = new Car( "5", "mazda","red",2021,20000, Arrays.asList(bookingDetails1,bookingDetails2));
        BookingDetails bookingDetails3 = new BookingDetails(3L,"10 Jul","04 Aug",2000,"reserved","brasov","cluj");
        Car car2 = new Car("cx-5", "mazda","red",2020,40000, List.of(bookingDetails3));
        List<Car> cars = Arrays.asList(car1, car2);

        // Configure the mock behavior
        when(carRepository.findAll()).thenReturn(cars);

        // Send GET request to the endpoint
        ResponseEntity<List<CarsDTOStatisticsBookingPrice>> response = restTemplate.exchange(
                "/cars/statistics",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CarsDTOStatisticsBookingPrice>>() {
                });

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<CarsDTOStatisticsBookingPrice> result = response.getBody();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(car1.getModel(), result.get(1).getModel());
        assertEquals(car1.getBrand(), result.get(1).getBrand());
        assertEquals(car1.getColor(), result.get(1).getColor());
        assertEquals(car1.getYear_manufacture(), result.get(1).getYear_manufacture());
        assertEquals(car1.getNrkilometers(), result.get(1).getNrkilometers());
        assertEquals(1500, (int) result.get(1).getAgvBookingPrice());
        assertEquals(car2.getYear_manufacture(), result.get(0).getYear_manufacture());
        assertEquals(car2.getNrkilometers(), result.get(0).getNrkilometers());
        assertEquals(car2.getModel(), result.get(0).getModel());
        assertEquals(car2.getBrand(), result.get(0).getBrand());
        assertEquals(car2.getColor(), result.get(0).getColor());
        assertEquals(2000, (int) result.get(0).getAgvBookingPrice());
    }
}




