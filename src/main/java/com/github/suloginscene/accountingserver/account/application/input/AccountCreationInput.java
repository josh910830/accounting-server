package com.github.suloginscene.accountingserver.account.application.input;

import com.github.suloginscene.accountingserver.account.domain.AccountType;
import com.github.suloginscene.accountingserver.common.Holder;
import com.github.suloginscene.accountingserver.common.Money;
import lombok.Data;


@Data
public class AccountCreationInput {

    private final Holder holder;

    private final AccountType type;
    private final String name;
    private final Money money;

}
