package com.github.suloginscene.accountant.context.account.domain.account;


public class AccountTypeNotFoundException extends IllegalArgumentException {

    AccountTypeNotFoundException(AccountType accountType) {
        super(accountType.name());
    }

}
