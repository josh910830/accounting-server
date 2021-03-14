package com.github.suloginscene.accountant.context.account.application;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.account.domain.transaction.DoubleTransactionExecutedEvent;
import com.github.suloginscene.accountant.context.account.domain.transaction.DoubleTransactionType;
import com.github.suloginscene.accountant.context.common.event.AccountantEventPublisher;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import com.github.suloginscene.accountant.testing.db.RepositoryFacade;
import com.github.suloginscene.accountant.testing.fixture.DefaultAccounts;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;


@SpringBootTest
@DisplayName("거래 실행 서비스")
class TransactionExecutingServiceTest {

    @Autowired TransactionExecutingService transactionExecutingService;
    @Autowired RepositoryFacade repositoryFacade;

    @SpyBean AccountantEventPublisher accountantEventPublisher;

    DoubleTransactionType sell;

    Account revenue;
    Account asset;

    Money amount;
    String description;


    @BeforeEach
    void setup() {
        sell = DoubleTransactionType.SELL;

        revenue = DefaultAccounts.revenue();
        asset = DefaultAccounts.asset(1);

        amount = Money.of(1);
        description = "설명";
    }

    @AfterEach
    void clear() {
        repositoryFacade.clear();
    }


    @Test
    @DisplayName("성공 - 이벤트 발행")
    void executeTransaction_onSuccess_publishesEvent() {
        repositoryFacade.given(revenue, asset);

        Long from = revenue.getId();
        Long to = asset.getId();
        TransactionExecutionData data = new TransactionExecutionData(sell, from, to, amount, description);
        transactionExecutingService.executeTransaction(data);

        then(accountantEventPublisher).should().publish(any(DoubleTransactionExecutedEvent.class));
    }

}
