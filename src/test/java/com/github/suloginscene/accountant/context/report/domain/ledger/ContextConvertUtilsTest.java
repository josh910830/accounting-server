package com.github.suloginscene.accountant.context.report.domain.ledger;

import com.github.suloginscene.accountant.context.common.event.DoubleTransactionExecutedEvent;
import com.github.suloginscene.accountant.testing.fixture.DefaultEvents;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("컨텍스트 변환 유틸리티")
class ContextConvertUtilsTest {

    DoubleTransactionExecutedEvent event;


    @BeforeEach
    void setup() {
        event = DefaultEvents.doubleTransactionExecutedEvent();
    }


    @Test
    @DisplayName("복식 거래 이벤트 - 값 변환")
    void toDoubleTransaction_onSuccess_convertByContext() {
        DoubleTransaction doubleTransaction = ContextConvertUtils.toDoubleTransaction(event);

        assertThat(doubleTransaction.getType().name()).isEqualTo(event.getType());
        assertThat(doubleTransaction.getDebit()).isEqualTo(event.getTo());
        assertThat(doubleTransaction.getCredit()).isEqualTo(event.getFrom());
    }

}
