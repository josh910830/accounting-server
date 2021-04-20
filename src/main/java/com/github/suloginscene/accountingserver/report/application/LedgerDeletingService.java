package com.github.suloginscene.accountingserver.report.application;

import com.github.suloginscene.accountingserver.common.Holder;
import com.github.suloginscene.accountingserver.report.domain.ledger.LedgerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class LedgerDeletingService {

    private final LedgerRepository ledgerRepository;


    public void deleteLedger(Holder holder) {
        if (ledgerRepository.existsById(holder)) {
            ledgerRepository.deleteWithChildren(holder);
        }
    }

}
