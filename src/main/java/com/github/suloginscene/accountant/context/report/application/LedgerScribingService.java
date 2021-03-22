package com.github.suloginscene.accountant.context.report.application;

import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import com.github.suloginscene.accountant.context.report.domain.ledger.DoubleTransaction;
import com.github.suloginscene.accountant.context.report.domain.ledger.Ledger;
import com.github.suloginscene.accountant.context.report.domain.ledger.LedgerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class LedgerScribingService {

    private final LedgerRepository ledgerRepository;


    public void scribeLedger(Holder holder, DoubleTransaction doubleTransaction) {
        Ledger ledger = ledgerRepository.findByIdOrElseNewSaved(holder);
        ledger.writeDoubleTransaction(doubleTransaction);
    }

}
