package com.github.suloginscene.accountant.testing.db;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.account.domain.account.AccountRepository;
import com.github.suloginscene.accountant.context.report.domain.ledger.LedgerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class RepositoryProxy {

    private static final String FORMAT = "======= %s =======\n";

    private final AccountRepository accountRepository;
    private final LedgerRepository ledgerRepository;


    public void given(Account... accounts) {
        for (Account account : accounts) {
            accountRepository.save(account);
        }
        printGivenFinished();
    }

    public void clear() {
        printClearStarted();
        accountRepository.deleteAll();
        ledgerRepository.deleteAll();
    }


    private void printGivenFinished() {
        System.out.printf(FORMAT, "given finished");
        System.out.println();
    }

    private void printClearStarted() {
        System.out.println();
        System.out.printf(FORMAT, "clear started");
    }

}
