package com.github.suloginscene.accountant.account.domain;

import com.github.suloginscene.accountant.common.Holder;
import com.github.suloginscene.accountant.common.Money;
import lombok.Data;


@Data
public class AccountCreationParameter {

    private final AccountType type;

    private final Holder holder;
    private final String name;
    private final Money money;

}
