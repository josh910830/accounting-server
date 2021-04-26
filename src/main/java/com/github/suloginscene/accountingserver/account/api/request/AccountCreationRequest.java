package com.github.suloginscene.accountingserver.account.api.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.github.suloginscene.accountingserver.account.api.request.AccountRegexps.NAME_MESSAGE;
import static com.github.suloginscene.accountingserver.account.api.request.AccountRegexps.NAME_REGEXP;


@Data
public class AccountCreationRequest {

    @NotNull(message = "계정 유형을 입력하십시오.")
    private final String type;

    @NotNull(message = "계정 이름을 입력하십시오.")
    @Pattern(regexp = NAME_REGEXP, message = NAME_MESSAGE)
    private final String name;

    @NotNull
    @Min(value = 0, message = "금액은 음수일 수 없습니다.")
    private final Integer money;

}
