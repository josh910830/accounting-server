package com.github.suloginscene.accountant.context.report.domain.ledger;

import com.github.suloginscene.accountant.context.common.event.DoubleTransactionExecutedEvent;


public class ContextConvertUtils {

    public static DoubleTransaction toDoubleTransaction(DoubleTransactionExecutedEvent event) {
        DoubleTransactionType type = DoubleTransactionType.valueOf(event.getType());
        String credit = event.getFrom();
        String debit = event.getTo();
        return new DoubleTransaction(
                type, debit, credit, event.getAmount(), event.getDescription(), event.getCreatedAt());
    }

}
