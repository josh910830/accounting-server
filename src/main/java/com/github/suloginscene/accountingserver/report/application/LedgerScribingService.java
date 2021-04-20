package com.github.suloginscene.accountingserver.report.application;

import com.github.suloginscene.accountingserver.account.domain.Account;
import com.github.suloginscene.accountingserver.common.Holder;
import com.github.suloginscene.accountingserver.common.Money;
import com.github.suloginscene.accountingserver.report.domain.ledger.DoubleTransactionType;
import com.github.suloginscene.accountingserver.report.domain.ledger.Ledger;
import com.github.suloginscene.accountingserver.report.domain.ledger.LedgerRepository;
import com.github.suloginscene.accountingserver.report.listener.TransactionInformation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class LedgerScribingService {

    private final LedgerRepository ledgerRepository;


    public void scribeLedger(Holder holder, TransactionInformation information) {
        DoubleTransactionType type = information.getType();
        Account debit = information.getDebit();
        Account credit = information.getCredit();
        Money amount = information.getAmount();
        String description = information.getDescription();

        Ledger ledger = ledgerRepository.findByIdOrElseNewSaved(holder);
        ledger.scribe(type, debit, credit, amount, description);
    }

}
