package com.booker.booking.persistence.entity;

import com.booker.customer.dto.response.CustomerResponse;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerBooking {

    private String id;
    private String countryOfOrigin;
    private String documentNumber;
    private String fullName;
    private LocalDate dateOfBirth;
    private String addressInCountryOfOrigin;
    private String phone;
    private String email;

    public static CustomerBooking fromCustomerResponse(CustomerResponse customerResponse) {
        CustomerBooking customerBooking = new CustomerBooking();
        customerBooking.setId(customerResponse.getId());
        customerBooking.setCountryOfOrigin(customerResponse.getCountry());
        customerBooking.setDocumentNumber(customerResponse.getDocumentNumber());
        customerBooking.setFullName(customerResponse.getFullName());
        customerBooking.setDateOfBirth(customerResponse.getDateOfBirth());
        customerBooking.setAddressInCountryOfOrigin(customerResponse.getAddress());
        customerBooking.setPhone(customerResponse.getPhone());
        customerBooking.setEmail(customerResponse.getEmail());
        return customerBooking;
    }
}
