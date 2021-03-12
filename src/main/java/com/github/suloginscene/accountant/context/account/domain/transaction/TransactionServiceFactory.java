package com.github.suloginscene.accountant.context.account.domain.transaction;


public class TransactionServiceFactory {

    public static TransactionService create(TransactionType transactionType) {
        switch (transactionType) {
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
                throw new TransactionTypeNotFoundException(transactionType);
        }
    }

}
