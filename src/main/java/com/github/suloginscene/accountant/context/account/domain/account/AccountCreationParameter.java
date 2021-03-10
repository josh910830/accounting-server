package com.github.suloginscene.accountant.context.account.domain.account;

import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import lombok.Data;


@Data
public class AccountCreationParameter {

    private final AccountType accountType;

    private final Holder holder;
    private final String name;
    private final Money money;

}
