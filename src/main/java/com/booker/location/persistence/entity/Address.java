package com.booker.location.persistence.entity;

import com.booker.location.dto.request.AddressRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String street;
    private String zip;
    private String city;
    private String state;

    public static Address fromAddressRequest(AddressRequest addressRequest) {
        Address address = new Address();
        address.setStreet(addressRequest.getStreet());
        address.setZip(addressRequest.getZip());
        address.setCity(addressRequest.getCity());
        address.setState(addressRequest.getState());
        return address;
    }
}
