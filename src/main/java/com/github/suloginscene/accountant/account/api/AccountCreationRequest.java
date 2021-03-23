package com.github.suloginscene.accountant.account.api;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Data
public class AccountCreationRequest {

    @NotNull
    private final String type;

    @NotNull
    @Pattern(regexp = "^[가-힣a-zA-Z0-9\\s]{1,8}$", message = "1-8자의 일반문자와 공백")
    private final String name;

    @NotNull
    @Min(value = 0, message = "최소 0")
    private final Integer money;

}
