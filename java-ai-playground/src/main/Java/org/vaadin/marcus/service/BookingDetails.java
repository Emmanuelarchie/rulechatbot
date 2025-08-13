package org.vaadin.marcus.service;

import java.time.LocalDate;

public record BookingDetails(String bookingNumber,
                             String firstName,
                             String lastName,
                             LocalDate date,
                             String from,
                             String to
                             ) {
}
