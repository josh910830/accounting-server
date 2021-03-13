package com.github.suloginscene.accountant.context.account.domain.transaction;

import com.github.suloginscene.accountant.context.account.domain.account.Account;


public class HolderNotMatchedException extends IllegalStateException {

    HolderNotMatchedException(Account a, Account b) {
        super(a + " and " + b + " should have equal holder, but " + a.getHolder() + " and " + b.getHolder());
    }

}
