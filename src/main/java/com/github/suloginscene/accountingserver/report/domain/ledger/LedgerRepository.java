package com.github.suloginscene.accountingserver.report.domain.ledger;

import com.github.suloginscene.accountingserver.common.Holder;
import com.github.suloginscene.exception.NotFoundException;
import com.github.suloginscene.profile.ProfileChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class LedgerRepository {

    private final LedgerJpaRepository ledgerJpaRepository;
    private final ProfileChecker profileChecker;


    public boolean existsById(Holder holder) {
        return ledgerJpaRepository.existsById(holder);
    }

    public Ledger findById(Holder holder) {
        return ledgerJpaRepository.findById(holder)
                .orElseThrow(() -> new NotFoundException(Ledger.class, holder));
    }

    public Ledger save(Ledger ledger) {
        return ledgerJpaRepository.save(ledger);
    }

    public Ledger findByIdOrElseNewSaved(Holder holder) {
        return existsById(holder) ? findById(holder) : save(new Ledger(holder));
    }

    public void deleteWithChildren(Holder holder) {
        Ledger ledger = findById(holder);
        ledgerJpaRepository.bulkDeleteChildren(ledger);
        ledgerJpaRepository.delete(ledger);
    }


    public void deleteAll() {
        profileChecker.checkTest();
        ledgerJpaRepository.deleteAll();
    }

}
