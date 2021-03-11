package com.github.suloginscene.accountant.context.account.domain.transaction;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.account.domain.account.Asset;
import com.github.suloginscene.accountant.context.account.domain.account.Liability;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import lombok.NoArgsConstructor;

import static com.github.suloginscene.accountant.context.account.domain.transaction.AccountCastUtils.toAsset;
import static com.github.suloginscene.accountant.context.account.domain.transaction.AccountCastUtils.toLiability;
import static lombok.AccessLevel.PACKAGE;


@NoArgsConstructor(access = PACKAGE)
class RepayTransactionService implements TransactionService {

    @Override
    public DoubleTransaction execute(Account from, Account to, Money amount, String description) {
        Asset asset = toAsset(from);
        Liability liability = toLiability(to);

        asset.decrease(amount, description);
        liability.decrease(amount, description);

        return new DoubleTransaction();
    }

}
