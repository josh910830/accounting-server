package com.github.suloginscene.accountant.context.account.domain.transaction;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.common.value.money.Money;


public interface TransactionService {

    DoubleTransaction execute(Account from, Account to, Money amount, String description);

}
