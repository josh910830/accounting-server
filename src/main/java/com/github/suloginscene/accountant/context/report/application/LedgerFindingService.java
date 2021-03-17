package com.github.suloginscene.accountant.context.report.application;

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


    public Ledger findLedger(Holder holder) {
        return ledgerRepository.findById(holder).orElseThrow();
    }

}
