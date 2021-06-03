package com.github.suloginscene.accountingserver.account.api.request;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;


@NoArgsConstructor(access = PRIVATE)
public class AccountRegexps {

    public static final String NAME_REGEXP = "^[가-힣a-zA-Z0-9\\s]{1,8}$";
    public static final String NAME_MESSAGE = "계정 이름은 1-8자의 일반문자와 공백으로 이루어져야 합니다.";

}
