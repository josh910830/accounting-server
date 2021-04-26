package com.github.suloginscene.accountingserver.common;

import com.github.suloginscene.exception.RequestException;


class NegativeMoneyException extends RequestException {

    NegativeMoneyException(Integer amount) {
        super(amount.toString());
    }

}
