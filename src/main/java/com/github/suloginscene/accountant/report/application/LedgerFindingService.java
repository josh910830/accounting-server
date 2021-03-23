package com.github.suloginscene.accountant.report.application;

import com.github.suloginscene.accountant.account.domain.AccountRepository;
import com.github.suloginscene.accountant.common.Holder;
import com.github.suloginscene.accountant.report.domain.ledger.Ledger;
import com.github.suloginscene.accountant.report.domain.ledger.LedgerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LedgerFindingService {

    private final LedgerRepository ledgerRepository;
    private final AccountRepository accountRepository;


    public LedgerData findLedger(Holder holder) {
        preLoadAccountsToPreventNPlus1(holder);

        Ledger ledger = ledgerRepository.findByIdOrElseNewSaved(holder);
        return new LedgerData(ledger);
    }

    private void preLoadAccountsToPreventNPlus1(Holder holder) {
        accountRepository.findByHolder(holder);
    }

}
