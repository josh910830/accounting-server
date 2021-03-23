package com.github.suloginscene.accountant.testing.base;

import com.github.suloginscene.accountant.account.domain.account.Account;
import com.github.suloginscene.accountant.account.domain.account.AccountRepository;
import com.github.suloginscene.accountant.report.domain.ledger.Ledger;
import com.github.suloginscene.accountant.report.domain.ledger.LedgerRepository;
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
        logAround("given", () -> {
            for (Account account : accounts) {
                accountRepository.save(account);
            }
        });
    }

    protected void given(Ledger... ledgers) {
        logAround("given", () -> {
            for (Ledger ledger : ledgers) {
                ledgerRepository.save(ledger);
            }
        });
    }


    protected <T extends Account> T sync(T account) {
        ThreadLocal<T> temp = new ThreadLocal<>();
        logAround("sync", () -> {
            T found = (T) entityLoader.loadedAccount(account.getId());
            temp.set(found);
        });
        return temp.get();
    }

    protected Ledger sync(Ledger ledger) {
        ThreadLocal<Ledger> temp = new ThreadLocal<>();
        logAround("sync", () -> {
            Ledger found = entityLoader.loadedLedger(ledger.getHolder());
            temp.set(found);
        });
        return temp.get();
    }


    @AfterEach
    final void clearAllRepositories() {
        logAround("clear", () -> {
            ledgerRepository.deleteAll();
            accountRepository.deleteAll();
        });
    }


    private void logAround(String info, Runnable runnable) {
        log.debug("{} will be started.", info);
        runnable.run();
        log.debug("{} is finished.", info);
    }

}
