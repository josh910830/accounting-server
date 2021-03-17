package com.github.suloginscene.accountant.context.account.domain.transaction;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.github.suloginscene.accountant.context.account.domain.transaction.TransactionType.PURCHASE_BY_CASH;
import static com.github.suloginscene.accountant.testing.fixture.DefaultAccounts.asset;
import static com.github.suloginscene.accountant.testing.fixture.DefaultAccounts.expense;
import static com.github.suloginscene.accountant.testing.fixture.DefaultValues.AMOUNT;
import static com.github.suloginscene.accountant.testing.fixture.DefaultValues.DESCRIPTION;
import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("거래 도메인 서비스")
class TransactionServiceTest {

    @Test
    @DisplayName("복식 거래 이벤트 반환")
    void execute_onSuccess_returnsDoubleTransactionEvent() {
        Account source = asset(1);
        Account destination = expense(1);
        AccountPair pair = AccountPair.of(source, destination);

        TransactionService transaction = TransactionServiceFactory.create(PURCHASE_BY_CASH);
        TransactionExecutionParameter param = new TransactionExecutionParameter(pair, AMOUNT, DESCRIPTION);
        TransactionExecutedEvent event = transaction.execute(param);

        assertThat(event).isNotNull();
    }

}
