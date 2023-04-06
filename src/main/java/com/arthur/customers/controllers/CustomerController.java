package com.arthur.customers.controllers;

import com.arthur.customers.models.Customer;
import com.arthur.customers.models.dtos.CreateCustomerDTO;
import com.arthur.customers.mappers.CustomerMapper;
import com.arthur.customers.models.dtos.CustomerDTO;
import com.arthur.customers.models.dtos.UpdateCustomerDTO;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import com.arthur.customers.services.CustomerService;

@RequestMapping("/customers")
@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public List<Customer> getAllCustomers () {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById (@PathVariable String id) {
        Optional<Customer> customer = customerService.getCustomerById(id);
        return customer.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createCustomer (@Valid @RequestBody CreateCustomerDTO customerDTO) {
        try {
            Customer costumer = new Customer();

            costumer.setName(customerDTO.getName());
            costumer.setPhoneNumber(customerDTO.getPhoneNumber());
            costumer.setEmail(customerDTO.getEmail());
            costumer.setAge(customerDTO.getAge());

            Customer createdCustomer = customerService.createCustomer(costumer);

            return ResponseEntity.created(URI.create("/customers/" + createdCustomer.getId())).body(createdCustomer);
        } catch(IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable String id, @Valid @RequestBody UpdateCustomerDTO updateCustomerDTO) {
        Customer updatedCustomer = customerService.updateCustomer(id, updateCustomerDTO);
        CustomerDTO updatedCustomerDTO = new CustomerMapper().toDTO(updatedCustomer);

        return ResponseEntity.ok(updatedCustomerDTO);
    }

    @DeleteMapping
    public void deleteAllCustomers () {
        this.customerService.deleteAllCustomers();
    }

    @DeleteMapping("/{id}")
    public void deleteCustomerById (@PathVariable String id) {
        this.customerService.deleteCustomerById(id);
    }
}
