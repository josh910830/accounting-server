package com.github.suloginscene.accountant.testing.base;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.account.domain.account.AccountRepository;
import com.github.suloginscene.accountant.context.report.domain.ledger.Ledger;
import com.github.suloginscene.accountant.context.report.domain.ledger.LedgerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@Slf4j
public abstract class IntegrationTest {

    @Autowired AccountRepository accountRepository;
    @Autowired LedgerRepository ledgerRepository;

    @Autowired EntityLoader entityLoader;


    protected void given(Account... accounts) {
        for (Account account : accounts) {
            accountRepository.save(account);
        }
    }

    protected void given(Ledger... ledgers) {
        for (Ledger ledger : ledgers) {
            ledgerRepository.save(ledger);
        }
    }


    protected Account sync(Account account) {
        return entityLoader.loadedAccount(account.getId());
    }

    protected Ledger sync(Ledger ledger) {
        return entityLoader.loadedLedger(ledger.getHolder());
    }


    @AfterEach
    final void clearAllRepositories() {
        ledgerRepository.deleteAll();
        accountRepository.deleteAll();
    }

}
