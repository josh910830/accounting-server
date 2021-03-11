package com.github.suloginscene.accountant.context.account.domain.transaction;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.account.domain.account.Asset;
import com.github.suloginscene.accountant.context.account.domain.account.Revenue;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PACKAGE;


@NoArgsConstructor(access = PACKAGE)
class SellTransactionService implements TransactionService {

    @Override
    public DoubleTransaction execute(Account from, Account to, Money amount, String description) {
        // TODO create converter
        Revenue revenue = (Revenue) from;
        Asset asset = (Asset) to;

        revenue.occur(amount, description);
        asset.increase(amount, description);

        return new DoubleTransaction();
    }

}
