package com.github.suloginscene.accountant.report.listener;

import com.github.suloginscene.accountant.transaction.domain.TransactionExecutedEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.github.suloginscene.accountant.testing.data.TestingEventFactory.transactionExecutedEvent;
import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("이벤트 변환 유틸리티")
class EventMappingUtilsTest {

    @Test
    @DisplayName("장부용 복식거래 변환 - 대차 사상")
    void toDoubleTransaction_onSuccess_mapDrCr() {
        TransactionExecutedEvent event = transactionExecutedEvent();

        TransactionInformation information = EventMappingUtils.mappedInformation(event);

        assertThat(information.getType().name()).isEqualTo(event.getType().name());
        assertThat(information.getDebit()).isEqualTo(event.getPair().getDestination());
        assertThat(information.getCredit()).isEqualTo(event.getPair().getSource());
    }

}
