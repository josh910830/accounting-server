package com.github.suloginscene.accountant.transaction.domain;

import com.github.suloginscene.accountant.lib.exception.InternalException;
import com.github.suloginscene.accountant.transaction.domain.impl.BorrowTransactionService;
import com.github.suloginscene.accountant.transaction.domain.impl.PurchaseByCashTransactionService;
import com.github.suloginscene.accountant.transaction.domain.impl.PurchaseByCreditTransactionService;
import com.github.suloginscene.accountant.transaction.domain.impl.RepayTransactionService;
import com.github.suloginscene.accountant.transaction.domain.impl.SellTransactionService;
import com.github.suloginscene.accountant.transaction.domain.impl.TransferTransactionService;


public class TransactionServiceFactory {

    public static TransactionService create(TransactionType type) {
        switch (type) {
            case SELL:
                return new SellTransactionService();
            case PURCHASE_BY_CASH:
                return new PurchaseByCashTransactionService();
            case PURCHASE_BY_CREDIT:
                return new PurchaseByCreditTransactionService();
            case BORROW:
                return new BorrowTransactionService();
            case REPAY:
                return new RepayTransactionService();
            case TRANSFER:
                return new TransferTransactionService();
            default:
                throw new InternalException("enum is not handled");
        }
    }

}
