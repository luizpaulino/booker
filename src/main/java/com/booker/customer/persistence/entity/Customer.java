package com.booker.customer.persistence.entity;

import com.booker.customer.dto.request.CustomerRequest;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Document("customers")
public class Customer {

    @Id
    private String id;
    private String country;
    private String documentNumber;
    private String fullName;
    @DateTimeFormat(pattern = "yyy/MM/dd")
    private LocalDate dateOfBirth;
    private String address;
    private String phone;
    private String email;

    public static Customer fromCustomerRequest(CustomerRequest request) {
        Customer customer = new Customer();
        customer.setCountry(request.getCountry());
        customer.setDocumentNumber(request.getDocumentNumber());
        customer.setFullName(request.getFullName());
        customer.setDateOfBirth(request.getDateOfBirth());
        customer.setAddress(request.getAddress());
        customer.setPhone(request.getPhone());
        customer.setEmail(request.getEmail());
        return customer;
    }
}
