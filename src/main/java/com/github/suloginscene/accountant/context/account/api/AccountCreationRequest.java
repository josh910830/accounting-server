package com.github.suloginscene.accountant.context.account.api;

import lombok.Data;


@Data
public class AccountCreationRequest {

    private final String type;
    private final String name;
    private final Integer money;

}
