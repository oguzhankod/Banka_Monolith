package com.bank.banka.controller;

import com.bank.banka.dto.AccountDto;
import com.bank.banka.dto.request.CreateAccountRequest;
import com.bank.banka.dto.request.UpdateAccountRequest;
import com.bank.banka.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    @PostMapping
    public ResponseEntity<AccountDto> createAccountFromController(@RequestBody CreateAccountRequest createAccountRequest){
        return ResponseEntity.ok(accountService.createAccountFromService(createAccountRequest));
    }

    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccountsFromController(){
        return ResponseEntity.ok(accountService.getAllAccountsFromService());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountByIdFromController(@PathVariable Long id){
        return ResponseEntity.ok(accountService.getAccountByIdFromService(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountDto> updateAccountByIdFromController(@PathVariable Long id,
                                                                      @RequestBody UpdateAccountRequest updateAccountRequest){
        return ResponseEntity.ok(accountService.updateAccountFromService(id, updateAccountRequest));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AccountDto> deleteAccountFromController(@PathVariable Long id){
        accountService.deleteAccountFromService(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/withdraw/{id}/{amount}")
    public ResponseEntity<AccountDto> withdrayMoneyFromController(@PathVariable Long id,
                                                                  @PathVariable Double amount){
        return ResponseEntity.ok(accountService.withdrawMoneyFromService(id, amount));
    }

    @PutMapping("add/{id}/{amount}")
    public ResponseEntity<AccountDto> addMoneyFromController(@PathVariable Long id,
                                                             @PathVariable Double amount){
        return ResponseEntity.ok(accountService.addMoneyFromService(id, amount));
    }

}
