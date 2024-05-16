package com.bank.banka.service;

import com.bank.banka.dto.AccountDto;
import com.bank.banka.dto.CustomerDto;
import com.bank.banka.dto.convert.AccountDtoConverter;
import com.bank.banka.dto.request.CreateAccountRequest;
import com.bank.banka.dto.request.UpdateAccountRequest;
import com.bank.banka.exception.AccountNotFoundException;
import com.bank.banka.model.Account;
import com.bank.banka.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountDtoConverter accountDtoConverter;
    private final CustomerService customerService;

    public AccountService(AccountRepository accountRepository, AccountDtoConverter accountDtoConverter, CustomerService customerService) {
        this.accountRepository = accountRepository;
        this.accountDtoConverter = accountDtoConverter;
        this.customerService = customerService;
    }




    public AccountDto createAccountFromService(CreateAccountRequest createAccountRequest){
        Account account = Account.builder().
        accountId(createAccountRequest.getAccountId())
                .customerId(createAccountRequest.getCustomerId())
                .balance(createAccountRequest.getBalance())
                .currency(createAccountRequest.getCurrency())
                .build();

        return accountDtoConverter.convert(accountRepository.save(account));
    }


    public List<AccountDto> getAllAccountsFromService(){
       // List<Account> accounts = accountRepository.findAll();
       // return accounts.stream()
       //         .map(accountDtoConverter::convert).collect(Collectors.toList());

        return accountRepository.findAll()
                .stream()
                .map(accountDtoConverter::convert)
                .collect(Collectors.toList());
    }

    public AccountDto getAccountByIdFromService(Long id){

       // Optional<Account> accountOptional =  accountRepository.findById(id);

        return accountRepository.findById(id)
                .map(accountDtoConverter::convert)
                .orElseThrow(()->new AccountNotFoundException("Id'si verilen kullanıcı bulunamadı"));
    }

    public AccountDto updateAccountFromService(Long id, UpdateAccountRequest updateAccountRequest){

        CustomerDto customerDto =  customerService.getCustomerByIdFromService(updateAccountRequest.getCustomerId());
        if (customerDto.getId().equals("") || customerDto.getId() == null){
            return AccountDto.builder().build();
        }

        Optional<Account> accountOptional = accountRepository.findById(id);

        if (accountOptional.isPresent()){
            accountOptional.get().setCustomerId(updateAccountRequest.getCustomerId());
            accountOptional.get().setCity(updateAccountRequest.getCity());
            accountOptional.get().setBalance(updateAccountRequest.getBalance());
            accountOptional.get().setCurrency(updateAccountRequest.getCurrency());

            accountRepository.save(accountOptional.get());
        }

        return accountOptional.map(accountDtoConverter::convert)
                .orElseThrow(()->new AccountNotFoundException("Kullanıcı güncellenemedi"));
    }

    public void deleteAccountFromService(Long id){
        accountRepository.deleteById(id);
    }

    public AccountDto withdrawMoneyFromService(Long id, Double amount) {
        Optional<Account> accountOptional = accountRepository.findById(id);

        return accountOptional.map(account -> {
            if (account.getBalance() >= amount) {
                account.setBalance(account.getBalance() - amount);
                accountRepository.save(account);
                return accountDtoConverter.convert(account);
            } else {
                System.out.println("Insufficient funds -> accountId: " + id + " balance: " + account.getBalance() + " amount: " + amount);
                throw new AccountNotFoundException("Para çekilemedi");
            }
        }).orElseThrow(() -> new AccountNotFoundException("Hesap bulunamadı"));
    }


    public AccountDto addMoneyFromService(Long id,Double amount){
        Optional<Account> accountOptional = accountRepository.findById(id);

         accountOptional.ifPresent(account -> {
                account.setBalance(account.getBalance()+amount);
                accountRepository.save(account);
        });
        return accountOptional.map(accountDtoConverter::convert).
                orElseThrow(()->new AccountNotFoundException("Para yatıralamadı"));
    }

}
