package com.github.suloginscene.accountant.report.domain.ledger;

import com.github.suloginscene.accountant.account.domain.Account;
import com.github.suloginscene.accountant.common.Money;
import com.github.suloginscene.accountant.report.listener.TransactionInformation;
import com.github.suloginscene.accountant.transaction.domain.TransactionExecutedEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.suloginscene.accountant.report.listener.EventMappingUtils.mappedInformation;
import static com.github.suloginscene.accountant.testing.data.TestingConstants.TESTING_HOLDER;
import static com.github.suloginscene.accountant.testing.data.TestingEventFactory.transactionExecutedEvent;
import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("장부")
class LedgerTest {

    @Test
    @DisplayName("복식 거래 기록 - 기록 추가")
    void writeDoubleTransaction_onSuccess_adds() {
        TransactionExecutedEvent event = transactionExecutedEvent();
        TransactionInformation information = mappedInformation(event);

        DoubleTransactionType type = information.getType();
        Account debit = information.getDebit();
        Account credit = information.getCredit();
        Money amount = information.getAmount();
        String description = information.getDescription();

        Ledger ledger = new Ledger(TESTING_HOLDER);
        ledger.scribe(type, debit, credit, amount, description);

        assertThat(ledger.readDoubleTransactions()).hasSize(1);
    }

    @Test
    @DisplayName("복식 거래 조회 - 사본 반환")
    void readDoubleTransactions_onSuccess_returnsClonedList() {
        Ledger ledger = new Ledger(TESTING_HOLDER);

        List<DoubleTransaction> transactions1 = ledger.readDoubleTransactions();
        List<DoubleTransaction> transactions2 = ledger.readDoubleTransactions();

        assertThat(transactions1).isNotSameAs(transactions2);
    }

}
