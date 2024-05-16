package com.bank.banka.controller;

import com.bank.banka.dto.CustomerDto;
import com.bank.banka.dto.request.CreateCustomerRequest;
import com.bank.banka.dto.request.UpdateCustomerRequest;
import com.bank.banka.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomerFromController(){
        return ResponseEntity.ok(customerService.getAllCustomersFromService());
    }

    @PostMapping
    public ResponseEntity<CustomerDto> createCustomerFromController(@RequestBody CreateCustomerRequest createCustomerRequest){
        return ResponseEntity.ok(customerService.createCustomerFromService(createCustomerRequest));

    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomerByIdFromController(@PathVariable Long id){
        return ResponseEntity.ok(customerService.getCustomerByIdFromService(id));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomerDto> deleteCustomerByIdFromController(@PathVariable Long id){
        customerService.deleteCustomerFromService(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> updateCustomerByIdFromController(@PathVariable Long id,
                                                                        @RequestBody UpdateCustomerRequest updateCustomerRequest){

        return ResponseEntity.ok(customerService.updateCustomerByIdFromService(id,updateCustomerRequest));
    }


}
