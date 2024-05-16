package com.bank.banka.dto.request;

import com.bank.banka.dto.CityDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseCustomerRequest {

    private String name;
    private CityDto city;
    private String address;
}
