package com.github.suloginscene.accountingserver.account.api.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.github.suloginscene.accountingserver.account.api.request.AccountRegexps.NAME_MESSAGE;
import static com.github.suloginscene.accountingserver.account.api.request.AccountRegexps.NAME_REGEXP;


@EqualsAndHashCode @ToString
@NoArgsConstructor @AllArgsConstructor
public class AccountNameChangeRequest {

    @NotNull(message = "새 계정 이름을 입력하십시오.")
    @Pattern(regexp = NAME_REGEXP, message = NAME_MESSAGE)
    @Getter
    private String newName;

}
