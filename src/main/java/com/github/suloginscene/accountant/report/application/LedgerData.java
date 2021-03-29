package com.github.suloginscene.accountant.report.application;

import com.github.suloginscene.accountant.report.domain.ledger.DoubleTransaction;
import com.github.suloginscene.accountant.report.domain.ledger.Ledger;
import lombok.Data;

import java.util.List;

import static com.github.suloginscene.time.DateTimeFormatters.DATE_TIME;
import static java.util.stream.Collectors.toList;


@Data
public class LedgerData {

    private final List<DoubleTransactionData> doubleTransactions;


    public LedgerData(Ledger ledger) {
        doubleTransactions = ledger.readDoubleTransactions().stream()
                .map(DoubleTransactionData::new)
                .collect(toList());
    }


    @Data
    public static class DoubleTransactionData {

        private final String type;
        private final Integer amount;
        private final String debit;
        private final String credit;
        private final String description;
        private final String createdAt;


        public DoubleTransactionData(DoubleTransaction doubleTransaction) {
            type = doubleTransaction.getType().name();
            amount = doubleTransaction.getAmount().get();
            debit = doubleTransaction.getDebit().getName();
            credit = doubleTransaction.getCredit().getName();
            description = doubleTransaction.getDescription();
            createdAt = doubleTransaction.getCreatedAt().format(DATE_TIME);
        }

    }

}
