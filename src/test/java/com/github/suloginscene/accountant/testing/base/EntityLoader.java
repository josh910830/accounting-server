package com.github.suloginscene.accountant.testing.base;

import com.github.suloginscene.accountant.account.domain.Account;
import com.github.suloginscene.accountant.account.domain.AccountRepository;
import com.github.suloginscene.accountant.account.domain.SingleTransaction;
import com.github.suloginscene.accountant.common.Holder;
import com.github.suloginscene.accountant.report.domain.ledger.DoubleTransaction;
import com.github.suloginscene.accountant.report.domain.ledger.Ledger;
import com.github.suloginscene.accountant.report.domain.ledger.LedgerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EntityLoader {

    private final AccountRepository accountRepository;
    private final LedgerRepository ledgerRepository;


    public Account loadedAccount(Long id) {
        Account account = accountRepository.findById(id);
        account.readSingleTransactions().forEach(SingleTransaction::getDescription);
        return account;
    }

    public Ledger loadedLedger(Holder holder) {
        Ledger ledger = ledgerRepository.findByIdOrElseNewSaved(holder);
        ledger.readDoubleTransactions().forEach(DoubleTransaction::getDescription);
        return ledger;
    }

}
