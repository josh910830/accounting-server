package com.github.suloginscene.accountant.report.domain.ledger;

import com.github.suloginscene.accountant.lib.profile.ProfileChecker;
import com.github.suloginscene.accountant.common.Holder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class LedgerRepository {

    private final LedgerJpaRepository ledgerJpaRepository;
    private final ProfileChecker profileChecker;


    public Ledger findByIdOrElseNewSaved(Holder holder) {
        Optional<Ledger> optionalLedger = ledgerJpaRepository.findById(holder);

        if (optionalLedger.isPresent()) {
            return optionalLedger.get();
        } else {
            Ledger ledger = new Ledger(holder);
            return ledgerJpaRepository.save(ledger);
        }
    }


    public void save(Ledger ledger) {
        profileChecker.checkTest();
        ledgerJpaRepository.save(ledger);
    }

    public void deleteAll() {
        profileChecker.checkTest();
        ledgerJpaRepository.deleteAll();
    }

}
