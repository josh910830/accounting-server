package com.github.suloginscene.accountant.context.report.domain.ledger;

import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.suloginscene.accountant.context.report.domain.ledger.ContextConvertUtils.toDoubleTransaction;
import static com.github.suloginscene.accountant.testing.fixture.DefaultEvents.doubleTransactionExecutedEvent;
import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("장부")
class LedgerTest {

    Ledger ledger;
    DoubleTransaction doubleTransaction;


    @BeforeEach
    void setup() {
        Holder holder = new Holder(1L);
        ledger = new Ledger(holder);

        doubleTransaction = toDoubleTransaction(doubleTransactionExecutedEvent());
    }


    @Test
    @DisplayName("복식 거래 기록 - 기록 추가")
    void writeDoubleTransaction_onSuccess_adds() {
        ledger.writeDoubleTransaction(doubleTransaction);

        assertThat(ledger.readDoubleTransactions()).hasSize(1);
    }

    @Test
    @DisplayName("복식 거래 조회 - 사본 반환")
    void readDoubleTransactions_onSuccess_returnsClonedList() {
        List<DoubleTransaction> transactions1 = ledger.readDoubleTransactions();
        List<DoubleTransaction> transactions2 = ledger.readDoubleTransactions();

        assertThat(transactions1).isNotSameAs(transactions2);
    }

}
