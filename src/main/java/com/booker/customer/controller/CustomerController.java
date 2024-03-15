package com.booker.customer.controller;

import com.booker.customer.dto.request.CustomerRequest;
import com.booker.customer.dto.response.CustomerResponse;
import com.booker.customer.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/v1/customers")
public class CustomerController {

    private static final Logger logger = Logger.getLogger(CustomerController.class.getName());

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody @Valid CustomerRequest customerRequest) {
        CustomerResponse customerResponse = customerService.addCustomer(customerRequest);
        URI customerUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(customerResponse.getId())
                .toUri();
        return ResponseEntity.created(customerUri).body(customerResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable String id) {
        CustomerResponse customerResponse = customerService.getCustomerById(id);
        return ResponseEntity.ok(customerResponse);
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        List<CustomerResponse> customerResponses = customerService.getAllCustomers();
        return ResponseEntity.ok(customerResponses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(@RequestBody @Valid CustomerRequest customerRequest, @PathVariable String id) {
        CustomerResponse updatedCustomer = customerService.updateCustomer(id, customerRequest);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}