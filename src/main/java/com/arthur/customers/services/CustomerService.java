package com.arthur.customers.services;

import com.arthur.customers.exceptions.ResourceNotFoundException;
import com.arthur.customers.models.Customer;
import com.arthur.customers.models.dtos.UpdateCustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import com.arthur.customers.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers () {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(String id) {
        return customerRepository.findById(id);
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer updateCustomer (String id, UpdateCustomerDTO updateCustomerDTO) {
        Customer updatedCustomer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));

        if (updateCustomerDTO.getName() != null) {
            updatedCustomer.setName(updateCustomerDTO.getName());
        }

        if (updateCustomerDTO.getPhoneNumber() != null) {
            updatedCustomer.setPhoneNumber(updateCustomerDTO.getPhoneNumber());
        }

        if (updateCustomerDTO.getEmail() != null) {
            updatedCustomer.setEmail(updateCustomerDTO.getEmail());
        }

        Optional<Integer> customerAge = Optional.of(updateCustomerDTO.getAge());

        customerAge.ifPresent(updatedCustomer::setAge);

        customerRepository.save(updatedCustomer);

        return updatedCustomer;
    }

    public void deleteAllCustomers() {
        customerRepository.deleteAll();
    }

    public void deleteCustomerById(String id) {
        customerRepository.deleteById(id);
    }

}
