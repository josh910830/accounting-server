package com.github.suloginscene.accountant.testing.db;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.account.domain.account.AccountRepository;
import com.github.suloginscene.accountant.context.report.domain.ledger.Ledger;
import com.github.suloginscene.accountant.context.report.domain.ledger.LedgerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class RepositoryFacade {

    private final AccountRepository accountRepository;
    private final LedgerRepository ledgerRepository;


    public void given(Object... objects) {
        log.info("\n====== given started ======");
        for (Object object : objects) {
            if (object instanceof Account) {
                log.info("\n[ save - {} ]", object);
                accountRepository.save((Account) object);
                continue;
            }
            if (object instanceof Ledger) {
                log.info("\n[ save - {} ]", object);
                ledgerRepository.save((Ledger) object);
                continue;
            }
            throw new IllegalArgumentException(object + " has no repository");
        }
        log.info("\n====== given finished ======\n");
    }


    public void clear() {
        log.info("\n====== clear started ======");
        ledgerRepository.deleteAll();
        accountRepository.deleteAll();
        log.info("\n====== clear finished ======\n");
    }

}
