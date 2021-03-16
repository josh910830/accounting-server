package com.github.suloginscene.accountant.context.report.listener;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.account.domain.transaction.TransactionExecutedEvent;
import com.github.suloginscene.accountant.context.account.domain.transaction.TransactionType;
import com.github.suloginscene.accountant.context.report.domain.ledger.DoubleTransaction;
import com.github.suloginscene.accountant.testing.fixture.DefaultEvents;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("이벤트 변환 유틸리티")
class EventTransformUtilsTest {

    TransactionExecutedEvent event;


    @BeforeEach
    void setup() {
        event = DefaultEvents.transactionExecutedEvent();
    }


    @Test
    @DisplayName("장부용 복식거래 변환 - 대차 사상")
    void toDoubleTransaction_onSuccess_mapDrCr() {
        TransactionType type = event.getType();
        Account source = event.getPair().getSource();
        Account destination = event.getPair().getDestination();

        DoubleTransaction transaction = EventTransformUtils.toDoubleTransaction(event);

        assertThat(transaction.getType().name()).isEqualTo(type.name());
        assertThat(transaction.getDebit()).isEqualTo(destination);
        assertThat(transaction.getCredit()).isEqualTo(source);
    }

}
