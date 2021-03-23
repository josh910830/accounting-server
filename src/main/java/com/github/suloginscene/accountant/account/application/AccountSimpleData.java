package com.github.suloginscene.accountant.account.application;

import com.github.suloginscene.accountant.account.domain.Account;
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
