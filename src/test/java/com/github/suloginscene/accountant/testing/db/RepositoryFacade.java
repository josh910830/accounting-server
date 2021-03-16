package com.github.suloginscene.accountant.testing.db;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.account.domain.account.AccountRepository;
import com.github.suloginscene.accountant.context.report.domain.ledger.Ledger;
import com.github.suloginscene.accountant.context.report.domain.ledger.LedgerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class RepositoryFacade {

    private static final String DOUBLE_LINE = "======= %s =======\n";
    private static final String SINGLE_LINE = "------- %s -------\n";

    private final AccountRepository accountRepository;
    private final LedgerRepository ledgerRepository;


    public void given(Object... objects) {
        for (Object object : objects) {
            if (object instanceof Account) {
                printSave("account");
                accountRepository.save((Account) object);
                continue;
            }
            if (object instanceof Ledger) {
                printSave("ledger");
                ledgerRepository.save((Ledger) object);
                continue;
            }
            throw new IllegalArgumentException(object + " has no repository");
        }
        printGivenFinished();
    }

    private void printSave(String type) {
        System.out.printf(SINGLE_LINE, "save " + type);
    }

    private void printGivenFinished() {
        System.out.printf(DOUBLE_LINE, "given finished");
        System.out.println();
    }


    public void clear() {
        printClearStarted();
        ledgerRepository.deleteAll();
        accountRepository.deleteAll();
    }

    private void printClearStarted() {
        System.out.println();
        System.out.printf(DOUBLE_LINE, "clear started");
    }

}
