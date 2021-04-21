package com.github.suloginscene.accountingserver.transaction.application;

import com.github.suloginscene.accountingserver.account.domain.Account;
import com.github.suloginscene.accountingserver.testing.base.IntegrationTest;
import com.github.suloginscene.accountingserver.transaction.application.input.TransactionExecutionInput;
import com.github.suloginscene.accountingserver.transaction.domain.TransactionExecutedEvent;
import com.github.suloginscene.event.EventPublisher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static com.github.suloginscene.accountingserver.testing.data.TestingAccountFactory.asset;
import static com.github.suloginscene.accountingserver.testing.data.TestingAccountFactory.revenue;
import static com.github.suloginscene.accountingserver.testing.data.TestingConstants.DESCRIPTION;
import static com.github.suloginscene.accountingserver.testing.data.TestingConstants.MONEY_ONE;
import static com.github.suloginscene.accountingserver.transaction.domain.TransactionType.SELL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;


@DisplayName("거래 실행 서비스")
class TransactionExecutingServiceTest extends IntegrationTest {

    @Autowired TransactionExecutingService transactionExecutingService;
    @SpyBean EventPublisher eventPublisher;


    @Test
    @DisplayName("성공 - 실행 및 이벤트 발행")
    void executeTransaction_onSuccess_executesAndPublishesEvent() {
        Account revenue = revenue();
        Account asset = asset();
        given(revenue, asset);

        Long source = revenue.getId();
        Long destination = asset.getId();
        TransactionExecutionInput input = new TransactionExecutionInput(SELL, source, destination, MONEY_ONE, DESCRIPTION);
        transactionExecutingService.executeTransaction(input);

        revenue = sync(revenue);
        asset = sync(asset);
        assertThat(revenue.readSingleTransactions()).hasSize(1);
        assertThat(asset.readSingleTransactions()).hasSize(1 + 1);
        then(eventPublisher).should().publish(any(TransactionExecutedEvent.class));
    }

}
