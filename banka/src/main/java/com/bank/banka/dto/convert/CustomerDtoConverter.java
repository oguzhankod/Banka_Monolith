package com.bank.banka.dto.convert;

import com.bank.banka.dto.CityDto;
import com.bank.banka.dto.CustomerDto;
import com.bank.banka.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerDtoConverter {

    public CustomerDto convert(Customer customer){
        CustomerDto customerDto = CustomerDto.builder()
                .id(customer.getId())
                .name(customer.getName())
                .address(customer.getAddress())
                .city(CityDto.valueOf(customer.getCity().name())).build();
        return customerDto;
    }
}
