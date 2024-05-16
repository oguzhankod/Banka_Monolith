package com.bank.banka.dto.convert;

import com.bank.banka.dto.AccountDto;
import com.bank.banka.model.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountDtoConverter {

    public AccountDto convert(Account account){
        AccountDto accountDto = AccountDto.builder().
        accountId(account.getAccountId())
                .customerId(account.getCustomerId())
                .balance(account.getBalance())
                .currency(account.getCurrency())
                .build();
        return accountDto;
    }


}
