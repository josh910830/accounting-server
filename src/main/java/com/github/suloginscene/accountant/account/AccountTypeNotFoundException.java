package com.github.suloginscene.accountant.account;


public class AccountTypeNotFoundException extends IllegalArgumentException {

    AccountTypeNotFoundException(AccountType accountType) {
        super(accountType.name());
    }

}
