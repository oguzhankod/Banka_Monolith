package com.bank.banka.dto;

import com.bank.banka.model.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDto {

    private Long accountId;
    private Long customerId;
    private Double balance;
    private Currency currency;



}
