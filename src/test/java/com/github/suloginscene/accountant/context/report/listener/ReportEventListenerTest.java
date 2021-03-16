package com.github.suloginscene.accountant.context.report.listener;

import com.github.suloginscene.accountant.context.account.domain.transaction.TransactionExecutedEvent;
import com.github.suloginscene.accountant.context.common.event.AccountantEventPublisher;
import com.github.suloginscene.accountant.context.report.application.LedgerScribingService;
import com.github.suloginscene.accountant.testing.fixture.DefaultEvents;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;


@SpringBootTest
@DisplayName("보고서 이벤트 리스너")
class ReportEventListenerTest {

    @SpyBean ReportEventListener reportEventListener;
    @MockBean LedgerScribingService ledgerScribingService;
    @Autowired AccountantEventPublisher accountantEventPublisher;

    TransactionExecutedEvent event;


    @BeforeEach
    void setup() {
        event = DefaultEvents.transactionExecutedEvent();
    }


    @Test
    @DisplayName("이벤트 구독 - 장부 기록 서비스 호출")
    void onDoubleTransactionExecutedEvent_onSuccess_listens() {
        accountantEventPublisher.publish(event);

        then(reportEventListener).should().on(any(TransactionExecutedEvent.class));

        then(ledgerScribingService).should().scribeLedger(any(), any());
    }

}
