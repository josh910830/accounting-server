package com.github.suloginscene.accountant.report.listener;

import com.github.suloginscene.accountant.common.Holder;
import com.github.suloginscene.accountant.report.application.LedgerScribingService;
import com.github.suloginscene.accountant.report.domain.ledger.DoubleTransaction;
import com.github.suloginscene.accountant.transaction.domain.TransactionExecutedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static com.github.suloginscene.accountant.report.listener.EventTransformUtils.toDoubleTransaction;


@Component
@RequiredArgsConstructor
public class ReportEventListener {

    private final LedgerScribingService ledgerScribingService;


    @EventListener
    public void on(TransactionExecutedEvent event) {
        Holder holder = event.getHolder();
        DoubleTransaction doubleTransaction = toDoubleTransaction(event);

        ledgerScribingService.scribeLedger(holder, doubleTransaction);
    }

}
