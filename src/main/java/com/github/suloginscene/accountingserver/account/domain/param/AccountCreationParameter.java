package com.github.suloginscene.accountingserver.account.domain.param;

import com.github.suloginscene.accountingserver.account.domain.AccountType;
import com.github.suloginscene.accountingserver.common.Holder;
import com.github.suloginscene.accountingserver.common.Money;
import lombok.Data;


@Data
public class AccountCreationParameter {

    private final AccountType type;

    private final Holder holder;
    private final String name;
    private final Money money;

}
