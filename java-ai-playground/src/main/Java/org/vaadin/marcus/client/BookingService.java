package org.vaadin.marcus.client;

import org.vaadin.marcus.service.BookingDetails;
import org.vaadin.marcus.service.ClinicService;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import java.util.List;

@BrowserCallable
@AnonymousAllowed
public class BookingService {
    private final ClinicService carRentalService;

    public BookingService(ClinicService carRentalService) {
        this.carRentalService = carRentalService;
    }

    public List<BookingDetails> getBookings() {
        return carRentalService.getBookings();
    }
}
