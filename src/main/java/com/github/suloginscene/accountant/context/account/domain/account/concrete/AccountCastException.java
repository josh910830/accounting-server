package com.github.suloginscene.accountant.context.account.domain.account.concrete;

import com.github.suloginscene.accountant.context.account.domain.account.Account;


public class AccountCastException extends ClassCastException {

    AccountCastException(Account object, Class<? extends Account> targetClass) {
        super(object.getId() + " is not " + targetClass.getSimpleName());
    }

}
