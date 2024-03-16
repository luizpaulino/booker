package com.booker.customer.service;

import com.booker.customer.dto.request.CustomerRequest;
import com.booker.customer.dto.response.CustomerResponse;
import com.booker.customer.persistence.entity.Customer;
import com.booker.customer.persistence.repository.CustomerRepository;
import com.booker.shared.exception.models.BookerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public CustomerResponse addCustomer(CustomerRequest customerRequest) {

        customerRepository.findByEmail(customerRequest.getEmail()).ifPresent(location -> {
            throw new BookerException("Customer with email " + customerRequest.getEmail() + " already exists");
        });

        validateDocumentNumber(customerRequest.getDocumentNumber(), customerRequest.getCountry());

        Customer customer = customerRepository.save(Customer.fromCustomerRequest(customerRequest));
        return CustomerResponse.fromCustomer(customer);
    }

    private void validateDocumentNumber(String documentNumber, String country) {
        if ("Brazil".equalsIgnoreCase(country)) {
            validateCPF(documentNumber);
            return;
        }

        if (!documentNumber.matches("[A-Za-z0-9]+")) {
            throw new BookerException("Invalid passport number: " + documentNumber);
        }
    }

    private void validateCPF(String cpf) {
        cpf = cpf.replaceAll("\\D", "");
        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            throw new BookerException("Invalid CPF: " + cpf);
        }

        int[] digits = cpf.chars().map(Character::getNumericValue).toArray();
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += digits[i] * (10 - i);
        }
        int mod = sum % 11;
        int firstDigit = (mod < 2) ? 0 : (11 - mod);

        sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += digits[i] * (11 - i);
        }
        sum += firstDigit * 2;
        mod = sum % 11;
        int secondDigit = (mod < 2) ? 0 : (11 - mod);

        if (firstDigit != digits[9] || secondDigit != digits[10]) {
            throw new BookerException("Invalid CPF: " + cpf);
        }
    }

    public CustomerResponse getCustomerById(String id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new BookerException("Customer not found with id: " + id));
        return CustomerResponse.fromCustomer(customer);
    }

    public List<CustomerResponse> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(CustomerResponse::fromCustomer)
                .toList();
    }

    public CustomerResponse updateCustomer(String id, CustomerRequest customerRequest) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new BookerException("Customer not found with id: " + id));

        if (!customer.getFullName().isBlank()) {
            customer.setFullName(customerRequest.getFullName());
        }

        if (!customer.getCountry().isBlank()) {
            customer.setCountry(customerRequest.getCountry());
        }

        if (customer.getDateOfBirth() != null) {
            customer.setDateOfBirth(customerRequest.getDateOfBirth());
        }

        if (!customer.getAddress().isBlank()) {
            customer.setAddress(customerRequest.getAddress());
        }

        if (!customer.getPhone().isBlank()) {
            customer.setPhone(customerRequest.getPhone());
        }

        Customer updatedCustomer = customerRepository.save(customer);
        return CustomerResponse.fromCustomer(updatedCustomer);
    }

    public void deleteCustomer(String id) {
        customerRepository.deleteById(id);
    }
}
