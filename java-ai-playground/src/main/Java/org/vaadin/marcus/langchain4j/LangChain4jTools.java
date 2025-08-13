package org.vaadin.marcus.langchain4j;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;
import org.vaadin.marcus.service.BookingDetails;
import org.vaadin.marcus.service.ClinicService;

import java.time.LocalDate;

@Component
public class LangChain4jTools {

    private final ClinicService service;

    public LangChain4jTools(ClinicService service) {
        this.service = service;
    }

    @Tool("""
            Retrieves information about an existing booking,
            such as the appointment date, booking status, , and booking department.
            """)
    public BookingDetails getBookingDetails(String bookingNumber, String firstName, String lastName) {
        return service.getBookingDetails(bookingNumber, firstName, lastName);
    }

    @Tool("""
            Modifies an existing booking.
            This includes making changes to the appointment date.
            """)
    public void changeBooking(
        String bookingNumber,
        String firstName,
        String lastName,
        LocalDate newFlightDate,
        @P("") String newDepartureAirport,
        @P("") String newArrivalAirport
    ) {
        service.changeBooking(bookingNumber, firstName, lastName, newFlightDate, newDepartureAirport, newArrivalAirport);
    }

    @Tool
    public void cancelBooking(String bookingNumber, String firstName, String lastName) {
        service.cancelBooking(bookingNumber, firstName, lastName);
    }

}
