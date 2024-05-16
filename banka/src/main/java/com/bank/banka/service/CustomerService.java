package com.bank.banka.service;


import com.bank.banka.dto.CityDto;
import com.bank.banka.dto.CustomerDto;
import com.bank.banka.dto.convert.CustomerDtoConverter;
import com.bank.banka.dto.request.CreateCustomerRequest;
import com.bank.banka.dto.request.UpdateCustomerRequest;
import com.bank.banka.exception.CustomerNotFoundException;
import com.bank.banka.model.City;
import com.bank.banka.model.Customer;
import com.bank.banka.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerDtoConverter customerDtoConverter;

    public CustomerService(CustomerRepository customerRepository, CustomerDtoConverter customerDtoConverter) {
        this.customerRepository = customerRepository;
        this.customerDtoConverter = customerDtoConverter;
    }

    public CustomerDto createCustomerFromService(CreateCustomerRequest createCustomerRequest) {
        Customer customer = new Customer();
        customer.setId(createCustomerRequest.getId());
        customer.setAddress(createCustomerRequest.getAddress());
        customer.setName(createCustomerRequest.getName());
        customer.setCity(City.valueOf(createCustomerRequest.getCity().name()));

        customerRepository.save(customer);

        return customerDtoConverter.convert(customer);
    }


    public List<CustomerDto> getAllCustomersFromService() {
        List<Customer> customers = customerRepository.findAll();

        List<CustomerDto> customerDtos = new ArrayList<>();

        for (Customer customer : customers) {
            customerDtos.add(customerDtoConverter.convert(customer));
        }
        return customerDtos;

    }

    public CustomerDto getCustomerByIdFromService(Long id) {
        Optional<Customer> customerOptinal = customerRepository.findById(id);

        return customerOptinal.map(customer -> customerDtoConverter.convert(customer))
                .orElseThrow(() -> new CustomerNotFoundException("Kullanıcı Bulunamadı"));
    }

    public void deleteCustomerFromService(Long id) {
        customerRepository.deleteById(id);
    }

    public CustomerDto updateCustomerByIdFromService(Long id, UpdateCustomerRequest updateCustomerRequest) {
        Optional<Customer> customerOptional = customerRepository.findById(id);

        if (customerOptional.isPresent()) {
            customerOptional.get().setId(id);
            customerOptional.get().setName(updateCustomerRequest.getName());
            customerOptional.get().setAddress(updateCustomerRequest.getAddress());
            customerOptional.get().setCity(City.valueOf(updateCustomerRequest.getCity().name()));

            customerRepository.save(customerOptional.get());

        }
        return customerOptional.map(customer -> customerDtoConverter.convert(customer))
                .orElseThrow(() -> new CustomerNotFoundException("Id'si Verilen Kullanıcı Bulunamadı"));

    }


}
