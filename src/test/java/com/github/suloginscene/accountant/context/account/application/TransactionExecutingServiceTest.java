package com.github.suloginscene.accountant.context.account.application;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.account.domain.transaction.TransactionType;
import com.github.suloginscene.accountant.context.common.event.AccountantEventPublisher;
import com.github.suloginscene.accountant.context.common.event.DoubleTransactionExecutedEvent;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import com.github.suloginscene.accountant.testing.db.RepositoryProxy;
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
    @Autowired RepositoryProxy repositoryProxy;

    @SpyBean AccountantEventPublisher accountantEventPublisher;

    TransactionType sell;

    Account revenue;
    Account asset;

    Money amount;
    String description;


    @BeforeEach
    void setup() {
        sell = TransactionType.SELL;

        revenue = DefaultAccounts.revenue();
        asset = DefaultAccounts.asset(1);

        amount = Money.of(1);
        description = "설명";
    }

    @AfterEach
    void clear() {
        repositoryProxy.clear();
    }


    @Test
    @DisplayName("성공 - 이벤트 발행")
    void executeTransaction_onSuccess_publishesEvent() {
        repositoryProxy.given(revenue, asset);

        Long from = revenue.getId();
        Long to = asset.getId();
        TransactionExecutionData data = new TransactionExecutionData(sell, from, to, amount, description);
        transactionExecutingService.executeTransaction(data);

        then(accountantEventPublisher).should().publish(any(DoubleTransactionExecutedEvent.class));
    }

}
