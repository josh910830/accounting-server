package com.github.suloginscene.accountant.context.account.domain.transaction;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PACKAGE;


@NoArgsConstructor(access = PACKAGE)
class PurchaseByCreditTransactionService implements TransactionService {

    @Override
    public DoubleTransaction execute(Account from, Account to, Money amount, String description) {
        return null;
    }

}
