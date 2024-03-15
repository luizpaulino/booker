package com.booker.location.dto.request;

import com.booker.location.persistence.entity.Address;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AddressRequest {

    @NotBlank
    private String street;

    @NotBlank
    private String zip;

    @NotBlank
    private String city;

    @NotBlank
    private String state;

    public AddressRequest fromAddressRequest(Address address) {
        AddressRequest addressRequest = new AddressRequest();
        address.setStreet(address.getStreet());
        address.setZip(address.getZip());
        address.setCity(address.getCity());
        address.setState(address.getState());
        return addressRequest;
    }
}
