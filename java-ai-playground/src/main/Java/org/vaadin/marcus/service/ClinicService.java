package org.vaadin.marcus.service;

import org.springframework.stereotype.Service;
import org.vaadin.marcus.data.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ClinicService {

    private final BookingData db;

    public ClinicService() {
        db = new BookingData();

        initDemoData();
    }

    private void initDemoData() {
        List<String> firstNames = List.of("Ekaette ", "Ali", "Frances ", "Gospel ", "Chuwee");
        List<String> lastNames = List.of("Archibong", "Cain", "Adewunmi", "Hope", "Osemeka");
        Random random = new Random();

        var customers = new ArrayList<Customer>();
        var bookings = new ArrayList<Booking>();

        for (int i = 0; i < 5; i++) {
            String firstName = firstNames.get(i);
            String lastName = lastNames.get(i);
            Customer customer = new Customer();
            customer.setFirstName(firstName);
            customer.setLastName(lastName);

            LocalDate date = LocalDate.now().plusDays(2*i);

            Booking booking = new Booking("10" + (i + 1), date, customer);
            customer.getBookings().add(booking);

            customers.add(customer);
            bookings.add(booking);
        }

        // Reset the database on each start
        db.setCustomers(customers);
        db.setBookings(bookings);

        System.out.println("Demo data initialized");
    }

    public List<BookingDetails> getBookings() {
        return db.getBookings().stream().map(this::toBookingDetails).toList();
    }

    private Booking findBooking(String bookingNumber, String firstName, String lastName) {
        return db.getBookings().stream()
                .filter(b -> b.getBookingNumber().equalsIgnoreCase(bookingNumber))
                .filter(b -> b.getCustomer().getFirstName().equalsIgnoreCase(firstName))
                .filter(b -> b.getCustomer().getLastName().equalsIgnoreCase(lastName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
    }

    public BookingDetails getBookingDetails(String bookingNumber, String firstName, String lastName) {
        var booking = findBooking(bookingNumber, firstName, lastName);
        return toBookingDetails(booking);
    }

    public void changeBooking(String bookingNumber, String firstName, String lastName,
                              LocalDate newFlightDate, String newDepartureAirport, String newArrivalAirport) {
        var booking = findBooking(bookingNumber, firstName, lastName);
        if(booking.getDate().isBefore(LocalDate.now().plusDays(1))){
            throw new IllegalArgumentException("Booking cannot be changed within 24 hours of the start date.");
        }
        booking.setDate(newFlightDate);
        booking.setFrom(newDepartureAirport);
        booking.setTo(newArrivalAirport);
    }

    public void cancelBooking(String bookingNumber, String firstName, String lastName) {
        var booking = findBooking(bookingNumber, firstName, lastName);
        if (booking.getDate().isBefore(LocalDate.now().plusDays(2))) {
            throw new IllegalArgumentException("Booking cannot be cancelled within 48 hours of the start date.");
        }
    }

    private BookingDetails toBookingDetails(Booking booking){
        return new BookingDetails(
                booking.getBookingNumber(),
                booking.getCustomer().getFirstName(),
                booking.getCustomer().getLastName(),
                booking.getDate(),
                booking.getFrom(),
                booking.getTo()
        );
    }
}
