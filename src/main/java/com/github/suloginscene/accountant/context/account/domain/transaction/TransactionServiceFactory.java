package com.github.suloginscene.accountant.context.account.domain.transaction;

import com.github.suloginscene.accountant.context.account.domain.transaction.impl.BorrowTransactionService;
import com.github.suloginscene.accountant.context.account.domain.transaction.impl.PurchaseByCashTransactionService;
import com.github.suloginscene.accountant.context.account.domain.transaction.impl.PurchaseByCreditTransactionService;
import com.github.suloginscene.accountant.context.account.domain.transaction.impl.RepayTransactionService;
import com.github.suloginscene.accountant.context.account.domain.transaction.impl.SellTransactionService;
import com.github.suloginscene.accountant.context.account.domain.transaction.impl.TransferTransactionService;
import com.github.suloginscene.accountant.context.common.exception.InternalException;


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
