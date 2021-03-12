package com.github.suloginscene.accountant.context.account.domain.transaction;


public class DoubleTransaction {

    private DoubleTransaction() {
    }

    static DoubleTransaction of() {
        return new DoubleTransaction();
    }

}
