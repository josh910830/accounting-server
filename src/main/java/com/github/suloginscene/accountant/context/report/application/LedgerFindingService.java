package com.github.suloginscene.accountant.context.report.application;

import com.github.suloginscene.accountant.context.account.domain.account.AccountRepository;
import com.github.suloginscene.accountant.context.common.exception.NotFoundException;
import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import com.github.suloginscene.accountant.context.report.domain.ledger.Ledger;
import com.github.suloginscene.accountant.context.report.domain.ledger.LedgerRepository;
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

        Ledger ledger = find(holder);

        return new LedgerData(ledger);
    }

    private Ledger find(Holder holder) {
        return ledgerRepository.findById(holder)
                .orElseThrow(() -> new NotFoundException(Ledger.class, holder));
    }

    private void preLoadAccountsToPreventNPlus1(Holder holder) {
        accountRepository.findByHolder(holder);
    }

}
