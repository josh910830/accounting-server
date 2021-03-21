package com.github.suloginscene.accountant.context.account.application;

import com.github.suloginscene.accountant.context.account.domain.account.Account;


public class AccountNotDeletableException extends IllegalStateException {

    AccountNotDeletableException(Account account) {
        super(account.getId() + " still has money");
    }

}
