package com.github.suloginscene.accountant.context.report.domain.ledger;

import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class LedgerRepository {

    private final LedgerJpaRepository ledgerJpaRepository;


    public Ledger findByIdOrElseNewSaved(Holder holder) {
        Optional<Ledger> optionalLedger = ledgerJpaRepository.findById(holder);

        if (optionalLedger.isPresent()) {
            return optionalLedger.get();
        } else {
            Ledger ledger = new Ledger(holder);
            return ledgerJpaRepository.save(ledger);
        }
    }


    // TODO on test
    public void save(Ledger ledger) {
        ledgerJpaRepository.save(ledger);
    }

    public void deleteAll() {
        ledgerJpaRepository.deleteAll();
    }

}
