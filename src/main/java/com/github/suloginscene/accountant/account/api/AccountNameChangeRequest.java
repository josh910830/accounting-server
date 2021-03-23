package com.github.suloginscene.accountant.account.api;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@EqualsAndHashCode @ToString
@NoArgsConstructor @AllArgsConstructor
public class AccountNameChangeRequest {

    @NotNull
    @Pattern(regexp = "^[가-힣a-zA-Z0-9\\s]{1,8}$", message = "1-8자의 일반문자와 공백")
    @Getter
    private String newName;

}
