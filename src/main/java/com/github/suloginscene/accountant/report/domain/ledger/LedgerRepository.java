package com.github.suloginscene.accountant.report.domain.ledger;

import com.github.suloginscene.accountant.common.Holder;
import com.github.suloginscene.exception.NotFoundException;
import com.github.suloginscene.profile.ProfileChecker;
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

    public boolean existsById(Holder holder) {
        return ledgerJpaRepository.existsById(holder);
    }

    public void deleteWithChildren(Holder holder) {
        Ledger ledger = findById(holder);
        ledgerJpaRepository.bulkDeleteChildren(ledger);
        ledgerJpaRepository.delete(ledger);
    }


    public Ledger findById(Holder holder) {
        profileChecker.checkTest();
        return ledgerJpaRepository.findById(holder)
                .orElseThrow(() -> new NotFoundException(Ledger.class, holder));
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
