package com.github.suloginscene.accountant.account.api;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.github.suloginscene.accountant.account.api.AccountRegexps.NAME_MESSAGE;
import static com.github.suloginscene.accountant.account.api.AccountRegexps.NAME_REGEXP;


@EqualsAndHashCode @ToString
@NoArgsConstructor @AllArgsConstructor
public class AccountNameChangeRequest {

    @NotNull
    @Pattern(regexp = NAME_REGEXP, message = NAME_MESSAGE)
    @Getter
    private String newName;

}
