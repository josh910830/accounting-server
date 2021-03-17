package com.github.suloginscene.accountant.context.report.application;

import com.github.suloginscene.accountant.context.report.domain.ledger.DoubleTransaction;
import com.github.suloginscene.accountant.context.report.domain.ledger.Ledger;
import lombok.Data;

import java.time.format.DateTimeFormatter;
import java.util.List;

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
            createdAt = doubleTransaction.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }

    }

}
