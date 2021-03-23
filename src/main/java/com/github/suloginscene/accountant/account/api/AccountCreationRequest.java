package com.github.suloginscene.accountant.account.api;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.github.suloginscene.accountant.account.api.AccountRegexps.NAME_MESSAGE;
import static com.github.suloginscene.accountant.account.api.AccountRegexps.NAME_REGEXP;


@Data
public class AccountCreationRequest {

    @NotNull
    private final String type;

    @NotNull
    @Pattern(regexp = NAME_REGEXP, message =NAME_MESSAGE)
    private final String name;

    @NotNull
    @Min(value = 0, message = "최소 0")
    private final Integer money;

}
