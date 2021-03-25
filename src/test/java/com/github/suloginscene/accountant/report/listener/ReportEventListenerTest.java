package com.github.suloginscene.accountant.report.listener;

import com.github.suloginscene.accountant.report.application.LedgerScribingService;
import com.github.suloginscene.accountant.transaction.domain.TransactionExecutedEvent;
import com.github.suloginscene.lib.event.EventPublisher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static com.github.suloginscene.accountant.testing.data.TestingEventFactory.transactionExecutedEvent;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;


@SpringBootTest
@DisplayName("보고서 이벤트 리스너")
class ReportEventListenerTest {

    @SpyBean ReportEventListener reportEventListener;
    @MockBean LedgerScribingService ledgerScribingService;
    @Autowired EventPublisher eventPublisher;


    @Test
    @DisplayName("이벤트 구독 - 장부 기록 서비스 호출")
    void onDoubleTransactionExecutedEvent_onSuccess_listens() {
        eventPublisher.publish(transactionExecutedEvent());

        then(reportEventListener).should().on(any(TransactionExecutedEvent.class));

        then(ledgerScribingService).should().scribeLedger(any(), any());
    }

}
