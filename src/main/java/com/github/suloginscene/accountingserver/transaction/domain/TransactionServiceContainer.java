package com.github.suloginscene.accountingserver.transaction.domain;

import com.github.suloginscene.accountingserver.transaction.domain.impl.BorrowTransactionService;
import com.github.suloginscene.accountingserver.transaction.domain.impl.PurchaseByCashTransactionService;
import com.github.suloginscene.accountingserver.transaction.domain.impl.PurchaseByCreditTransactionService;
import com.github.suloginscene.accountingserver.transaction.domain.impl.RepayTransactionService;
import com.github.suloginscene.accountingserver.transaction.domain.impl.SellTransactionService;
import com.github.suloginscene.accountingserver.transaction.domain.impl.TransferTransactionService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

import static com.github.suloginscene.accountingserver.transaction.domain.TransactionType.BORROW;
import static com.github.suloginscene.accountingserver.transaction.domain.TransactionType.PURCHASE_BY_CASH;
import static com.github.suloginscene.accountingserver.transaction.domain.TransactionType.PURCHASE_BY_CREDIT;
import static com.github.suloginscene.accountingserver.transaction.domain.TransactionType.REPAY;
import static com.github.suloginscene.accountingserver.transaction.domain.TransactionType.SELL;
import static com.github.suloginscene.accountingserver.transaction.domain.TransactionType.TRANSFER;


@Component
public class TransactionServiceContainer {

    private final Map<TransactionType, TransactionService> map = new HashMap<>();

    @PostConstruct
    void postConstruct() {
        map.put(SELL, new SellTransactionService());
        map.put(PURCHASE_BY_CASH, new PurchaseByCashTransactionService());
        map.put(PURCHASE_BY_CREDIT, new PurchaseByCreditTransactionService());
        map.put(BORROW, new BorrowTransactionService());
        map.put(REPAY, new RepayTransactionService());
        map.put(TRANSFER, new TransferTransactionService());
    }

    public TransactionService get(TransactionType type) {
        return map.get(type);
    }

}
