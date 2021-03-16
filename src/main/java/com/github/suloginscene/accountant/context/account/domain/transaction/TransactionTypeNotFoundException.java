package com.github.suloginscene.accountant.context.account.domain.transaction;


public class TransactionTypeNotFoundException extends IllegalArgumentException {

    TransactionTypeNotFoundException(TransactionType type) {
        super(type.name());
    }

}
