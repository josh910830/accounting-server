package com.github.suloginscene.accountant.context.account.domain.transaction;


public class DoubleTransactionTypeNotFoundException extends IllegalArgumentException {

    DoubleTransactionTypeNotFoundException(DoubleTransactionType type) {
        super(type.name());
    }

}
