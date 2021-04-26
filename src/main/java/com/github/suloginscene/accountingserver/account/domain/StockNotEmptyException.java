package com.github.suloginscene.accountingserver.account.domain;

import com.github.suloginscene.accountingserver.common.Money;
import com.github.suloginscene.exception.RequestException;


class StockNotEmptyException extends RequestException {

    StockNotEmptyException(Money balance) {
        super(balance.toString());
    }

}
