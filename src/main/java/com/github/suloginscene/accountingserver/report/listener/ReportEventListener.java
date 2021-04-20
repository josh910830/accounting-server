package com.github.suloginscene.accountingserver.report.listener;

import com.github.suloginscene.accountingserver.common.Holder;
import com.github.suloginscene.accountingserver.report.application.LedgerScribingService;
import com.github.suloginscene.accountingserver.transaction.domain.TransactionExecutedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static com.github.suloginscene.accountingserver.report.listener.EventMappingUtils.mappedInformation;


@Component
@RequiredArgsConstructor
public class ReportEventListener {

    private final LedgerScribingService ledgerScribingService;


    @EventListener
    public void on(TransactionExecutedEvent event) {
        Holder holder = event.getHolder();
        TransactionInformation information = mappedInformation(event);

        ledgerScribingService.scribeLedger(holder, information);
    }

}
