package com.github.suloginscene.accountant.context.report.application;

import com.github.suloginscene.accountant.context.account.domain.transaction.DoubleTransaction;
import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import com.github.suloginscene.accountant.context.report.domain.ledger.Ledger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class LedgerScribingService {

    private final LedgerProvider ledgerProvider;


    public void scribeLedger(Holder holder, DoubleTransaction doubleTransaction) {
        Ledger ledger = ledgerProvider.provideLedger(holder);
        ledger.writeDoubleTransaction(doubleTransaction);
    }

}
