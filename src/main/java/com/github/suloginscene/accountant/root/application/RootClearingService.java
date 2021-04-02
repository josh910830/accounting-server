package com.github.suloginscene.accountant.root.application;

import com.github.suloginscene.accountant.account.application.AccountConfiguringService;
import com.github.suloginscene.accountant.common.Holder;
import com.github.suloginscene.accountant.report.application.LedgerDeletingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class RootClearingService {

    private final LedgerDeletingService ledgerDeletingService;
    private final AccountConfiguringService accountConfiguringService;


    public void clearAll(Holder holder) {
        ledgerDeletingService.deleteLedger(holder);
        accountConfiguringService.deleteByHolder(holder);
    }

}
