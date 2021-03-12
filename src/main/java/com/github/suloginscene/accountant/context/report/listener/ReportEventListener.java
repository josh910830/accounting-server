package com.github.suloginscene.accountant.context.report.listener;

import com.github.suloginscene.accountant.context.common.event.DoubleTransactionExecutedEvent;
import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import com.github.suloginscene.accountant.context.report.application.LedgerScribingService;
import com.github.suloginscene.accountant.context.report.domain.ledger.DoubleTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


// TODO test
@Component
@RequiredArgsConstructor
public class ReportEventListener {

    private final LedgerScribingService ledgerScribingService;


    @EventListener
    public void on(DoubleTransactionExecutedEvent event) {
        Holder holder = event.getHolder();
        DoubleTransaction doubleTransaction = toDoubleTransaction(event);
        ledgerScribingService.scribeLedger(holder, doubleTransaction);
    }

    private DoubleTransaction toDoubleTransaction(DoubleTransactionExecutedEvent event) {
        String credit = event.getFrom();
        String debit = event.getTo();
        return new DoubleTransaction(
                debit, credit, event.getAmount(), event.getDescription(), event.getCreatedAt());
    }

}
