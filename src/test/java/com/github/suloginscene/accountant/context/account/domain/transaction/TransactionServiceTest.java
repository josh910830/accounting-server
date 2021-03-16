package com.github.suloginscene.accountant.context.account.domain.transaction;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.github.suloginscene.accountant.context.account.domain.transaction.DoubleTransactionType.PURCHASE_BY_CASH;
import static com.github.suloginscene.accountant.testing.fixture.DefaultAccounts.asset;
import static com.github.suloginscene.accountant.testing.fixture.DefaultAccounts.expense;
import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("거래 도메인 서비스")
class TransactionServiceTest {

    TransactionService transaction;

    AccountPair pair;
    Money amount;
    String description;


    @BeforeEach
    void setup() {
        transaction = TransactionServiceFactory.create(PURCHASE_BY_CASH);

        Account source = asset(1);
        Account destination = expense();
        pair = AccountPair.of(source, destination);
        amount = Money.of(1);
        description = "설명";
    }


    @Test
    @DisplayName("복식 거래 이벤트 반환")
    void execute_onSuccess_returnsDoubleTransactionEvent() {
        TransactionExecutionParameter param = new TransactionExecutionParameter(pair, amount, description);
        DoubleTransactionExecutedEvent event = transaction.execute(param);

        assertThat(event).isNotNull();
    }

}
