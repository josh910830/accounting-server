package com.github.suloginscene.accountingserver.account.application.data;

import com.github.suloginscene.accountingserver.account.domain.Account;
import lombok.Data;


@Data
public class AccountSimpleData {

    private final Long id;
    private final String name;
    private final String type;


    public AccountSimpleData(Account account) {
        id = account.getId();
        name = account.getName();
        type = account.getClass().getSimpleName();
    }

}
