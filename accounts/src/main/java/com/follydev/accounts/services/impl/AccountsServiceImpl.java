package com.follydev.accounts.services.impl;

import com.follydev.accounts.constants.AccountsConstants;
import com.follydev.accounts.dto.AccountsDto;
import com.follydev.accounts.dto.CustomerDto;
import com.follydev.accounts.entity.Accounts;
import com.follydev.accounts.entity.Customer;
import com.follydev.accounts.exceptions.CustomerAlreadyExistsException;
import com.follydev.accounts.exceptions.ResourceNotFoundException;
import com.follydev.accounts.mappers.AccountsMapper;
import com.follydev.accounts.mappers.CustomerMapper;
import com.follydev.accounts.repository.AccountRepository;
import com.follydev.accounts.repository.CustomerRepository;
import com.follydev.accounts.services.IAccountsService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import static com.follydev.accounts.mappers.AccountsMapper.mapToAccountsDto;
import static com.follydev.accounts.mappers.CustomerMapper.mapToCustomer;
import static com.follydev.accounts.mappers.CustomerMapper.mapToCustomerDto;

@Service
public class AccountsServiceImpl implements IAccountsService {

    private final AccountRepository accountRepository;

    private final CustomerRepository customerRepository;

    public AccountsServiceImpl(AccountRepository accountRepository,
                               CustomerRepository customerRepository) {

        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer  = mapToCustomer(customerDto, new Customer());
        Optional<Customer> customerOptional = customerRepository
                .findByMobileNumber(customerDto.getMobileNumber());

        if(customerOptional.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with this mobile number " +
                    customerDto.getMobileNumber());
        }

        Customer savedCustomer =  customerRepository.save(customer);
        accountRepository.save(createNewAccount(savedCustomer));
    }

    private Accounts createNewAccount(Customer customer) {

        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        return Accounts.builder()
                .customerId(customer.getCustomerId())
                .accountNumber(randomAccNumber)
                .accountType(AccountsConstants.SAVINGS)
                .branchAddress(AccountsConstants.ADDRESS)
                .build();

    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
       Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobile", mobileNumber)
        );

       Accounts accounts = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
               () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

       CustomerDto customerDto = mapToCustomerDto(customer, new CustomerDto());
       customerDto.setAccountsDto(mapToAccountsDto(accounts, new AccountsDto()));

       return customerDto;
    }
    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if(accountsDto != null) {
            Accounts accounts = accountRepository.findById(accountsDto.getAccountNumber())
                    .orElseThrow(() -> new ResourceNotFoundException("Account", "AccountNumber",
                            accountsDto.getAccountNumber().toString()));
            AccountsMapper.mapToAccounts(accountsDto, accounts);
            accountRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer", "customerId",
                            customerId.toString()));

            CustomerMapper.mapToCustomer(customerDto, customer);
            customerRepository.save(customer);
            isUpdated = true;
        }

        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
                );

        accountRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());

        return true;
    }

}
