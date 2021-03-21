package com.github.suloginscene.accountant.context.account.application;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.account.domain.transaction.TransactionExecutedEvent;
import com.github.suloginscene.accountant.context.common.event.AccountantEventPublisher;
import com.github.suloginscene.accountant.testing.base.IntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static com.github.suloginscene.accountant.context.account.domain.transaction.TransactionType.SELL;
import static com.github.suloginscene.accountant.testing.data.TestingAccountFactory.asset;
import static com.github.suloginscene.accountant.testing.data.TestingAccountFactory.revenue;
import static com.github.suloginscene.accountant.testing.data.TestingValues.DESCRIPTION;
import static com.github.suloginscene.accountant.testing.data.TestingValues.MONEY_ONE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;


@DisplayName("거래 실행 서비스")
class TransactionExecutingServiceTest extends IntegrationTest {

    @Autowired TransactionExecutingService transactionExecutingService;
    @SpyBean AccountantEventPublisher accountantEventPublisher;


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
        assertThat(asset.readSingleTransactions()).hasSize(1);
        then(accountantEventPublisher).should().publish(any(TransactionExecutedEvent.class));
    }

}
