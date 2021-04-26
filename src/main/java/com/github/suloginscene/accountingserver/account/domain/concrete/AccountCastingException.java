package com.github.suloginscene.accountingserver.account.domain.concrete;

import com.github.suloginscene.exception.RequestException;


class AccountCastingException extends RequestException {

    AccountCastingException(Long accountId, String targetClassName) {
        super(accountId + " is not " + targetClassName);
    }

}
