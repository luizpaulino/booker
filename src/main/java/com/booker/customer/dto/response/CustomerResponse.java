package com.booker.customer.dto.response;

import com.booker.customer.persistence.entity.Customer;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Getter
@Setter
public class CustomerResponse {


    private String id;
    private String country;
    private String documentNumber;
    private String fullName;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate dateOfBirth;
    private String address;
    private String phone;
    private String email;

    public static CustomerResponse fromCustomer(Customer customer) {
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setId(customer.getId());
        customerResponse.setCountry(customer.getCountry());
        customerResponse.setDocumentNumber(customer.getDocumentNumber());
        customerResponse.setFullName(customer.getFullName());
        customerResponse.setDateOfBirth(customer.getDateOfBirth());
        customerResponse.setAddress(customer.getAddress());
        customerResponse.setPhone(customer.getPhone());
        customerResponse.setEmail(customer.getEmail());
        return customerResponse;
    }
}
