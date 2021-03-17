package com.github.suloginscene.accountant.context.account.application;

import com.github.suloginscene.accountant.context.account.domain.account.concrete.Asset;
import com.github.suloginscene.accountant.context.account.domain.account.concrete.Revenue;
import com.github.suloginscene.accountant.context.account.domain.transaction.TransactionExecutedEvent;
import com.github.suloginscene.accountant.testing.base.IntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.github.suloginscene.accountant.context.account.domain.transaction.TransactionType.SELL;
import static com.github.suloginscene.accountant.testing.fixture.DefaultAccounts.asset;
import static com.github.suloginscene.accountant.testing.fixture.DefaultAccounts.revenue;
import static com.github.suloginscene.accountant.testing.fixture.DefaultValues.AMOUNT;
import static com.github.suloginscene.accountant.testing.fixture.DefaultValues.DESCRIPTION;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;


@DisplayName("거래 실행 서비스")
class TransactionExecutingServiceTest extends IntegrationTest {

    @Autowired TransactionExecutingService transactionExecutingService;


    @Test
    @DisplayName("성공 - 이벤트 발행")
    void executeTransaction_onSuccess_publishesEvent() {
        Revenue revenue = revenue(1);
        Asset asset = asset(1);
        repositoryFacade.given(revenue, asset);

        Long source = revenue.getId();
        Long destination = asset.getId();
        TransactionExecutionInput input = new TransactionExecutionInput(SELL, source, destination, AMOUNT, DESCRIPTION);
        transactionExecutingService.executeTransaction(input);

        then(accountantEventPublisher).should().publish(any(TransactionExecutedEvent.class));
    }

}
