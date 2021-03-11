package com.github.suloginscene.accountant.context.account.domain.transaction;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PACKAGE;


@NoArgsConstructor(access = PACKAGE)
class SellTransactionService implements TransactionService {

    @Override
    public DoubleTransaction execute(Account from, Account to) {
        return null;
    }

}
