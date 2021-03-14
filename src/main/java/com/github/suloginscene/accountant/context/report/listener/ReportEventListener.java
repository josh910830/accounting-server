package com.github.suloginscene.accountant.context.report.listener;

import com.github.suloginscene.accountant.context.account.domain.transaction.DoubleTransaction;
import com.github.suloginscene.accountant.context.account.domain.transaction.DoubleTransactionExecutedEvent;
import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import com.github.suloginscene.accountant.context.report.application.LedgerScribingService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ReportEventListener {

    private final LedgerScribingService ledgerScribingService;


    @EventListener
    public void on(DoubleTransactionExecutedEvent event) {
        Holder holder = event.getHolder();
        DoubleTransaction doubleTransaction = event.getDoubleTransaction();

        ledgerScribingService.scribeLedger(holder, doubleTransaction);
    }

}
