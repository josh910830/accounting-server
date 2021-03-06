package com.github.suloginscene.accountingserver.testing.base;

import com.github.suloginscene.accountingserver.account.domain.Account;
import com.github.suloginscene.accountingserver.account.domain.AccountRepository;
import com.github.suloginscene.accountingserver.account.domain.SingleTransaction;
import com.github.suloginscene.accountingserver.common.Holder;
import com.github.suloginscene.accountingserver.report.domain.ledger.DoubleTransaction;
import com.github.suloginscene.accountingserver.report.domain.ledger.Ledger;
import com.github.suloginscene.accountingserver.report.domain.ledger.LedgerRepository;
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
        Ledger ledger = ledgerRepository.findById(holder);
        ledger.readDoubleTransactions().forEach(DoubleTransaction::getDescription);
        return ledger;
    }

}
