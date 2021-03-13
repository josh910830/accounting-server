package com.github.suloginscene.accountant.context.report.application;

import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import com.github.suloginscene.accountant.context.report.domain.ledger.Ledger;
import com.github.suloginscene.accountant.context.report.domain.ledger.LedgerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class LedgerProvider {

    private final LedgerRepository ledgerRepository;


    public Ledger provideLedger(Holder holder) {
        Optional<Ledger> optionalLedger = ledgerRepository.findById(holder);

        if (optionalLedger.isPresent()) {
            return optionalLedger.get();
        } else {
            Ledger ledger = new Ledger(holder);
            return ledgerRepository.save(ledger);
        }
    }

}
