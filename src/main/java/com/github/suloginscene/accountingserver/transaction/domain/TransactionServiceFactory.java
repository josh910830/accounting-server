package com.github.suloginscene.accountingserver.transaction.domain;

import com.github.suloginscene.accountingserver.transaction.domain.impl.BorrowTransactionService;
import com.github.suloginscene.accountingserver.transaction.domain.impl.PurchaseByCashTransactionService;
import com.github.suloginscene.accountingserver.transaction.domain.impl.PurchaseByCreditTransactionService;
import com.github.suloginscene.accountingserver.transaction.domain.impl.RepayTransactionService;
import com.github.suloginscene.accountingserver.transaction.domain.impl.SellTransactionService;
import com.github.suloginscene.accountingserver.transaction.domain.impl.TransferTransactionService;
import com.github.suloginscene.exception.InternalException;


// TODO select factory or map after thinking about multi threading
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
