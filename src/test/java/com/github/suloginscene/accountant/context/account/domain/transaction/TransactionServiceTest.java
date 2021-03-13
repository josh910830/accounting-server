package com.github.suloginscene.accountant.context.account.domain.transaction;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.common.event.DoubleTransactionExecutedEvent;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.github.suloginscene.accountant.context.account.domain.transaction.TransactionType.PURCHASE_BY_CASH;
import static com.github.suloginscene.accountant.testing.fixture.DefaultAccounts.asset;
import static com.github.suloginscene.accountant.testing.fixture.DefaultAccounts.expense;
import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("거래 도메인 서비스")
class TransactionServiceTest {

    TransactionService transactionService;

    TransactionExecutionParameter param;


    @BeforeEach
    void setup() {
        TransactionType transactionType = PURCHASE_BY_CASH;
        transactionService = TransactionServiceFactory.create(transactionType);

        Account from = asset(1);
        Account to = expense();
        Money amount = Money.of(1);
        String description = "설명";
        param = new TransactionExecutionParameter(from, to, amount, description, transactionType);
    }


    @Test
    @DisplayName("복식 거래 이벤트 반환")
    void execute_onSuccess_returnsDoubleTransactionEvent() {
        DoubleTransactionExecutedEvent event = transactionService.execute(param);

        assertThat(event).isNotNull();
    }

}
