package com.follydev.accounts.mappers;

import com.follydev.accounts.dto.AccountsDto;
import com.follydev.accounts.entity.Accounts;

public class AccountsMapper {

    public static AccountsDto mapToAccountsDto(Accounts accounts, AccountsDto accountsDto) {
        return AccountsDto.builder()
                .accountNumber(accounts.getAccountNumber())
                .accountType(accounts.getAccountType())
                .branchAddress(accounts.getBranchAddress())
                .build();
    }

    public static Accounts mapToAccounts(AccountsDto accountsDto, Accounts accounts) {
        return Accounts.builder()
                .accountNumber(accountsDto.getAccountNumber())
                .accountType(accountsDto.getAccountType())
                .branchAddress(accountsDto.getBranchAddress())
                .build();
    }
}
