package com.bank.banka.dto.request;

import com.bank.banka.model.City;
import com.bank.banka.model.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseAccountRequest {

    private Long customerId;
    private Double balance;
    private Currency currency;
    private City city;
}
