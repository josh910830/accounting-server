package com.github.suloginscene.accountant.account;

import com.github.suloginscene.accountant.common.holder.Holder;
import com.github.suloginscene.accountant.common.money.Money;
import lombok.Data;


@Data
public class AccountCreationParameter {

    private final AccountType accountType;

    private final Holder holder;
    private final String name;
    private final Money money;

}
