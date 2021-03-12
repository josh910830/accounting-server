package com.github.suloginscene.accountant.context.account.application;

import com.github.suloginscene.accountant.context.account.domain.account.AccountType;
import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import lombok.Data;


@Data
public class AccountCreationData {

    private final AccountType accountType;

    private final Holder holder;
    private final String name;
    private final Money money;

}
