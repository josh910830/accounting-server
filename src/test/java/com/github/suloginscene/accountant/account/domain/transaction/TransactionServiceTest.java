package com.github.suloginscene.accountant.account.domain.transaction;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.github.suloginscene.accountant.account.domain.transaction.TransactionType.PURCHASE_BY_CASH;
import static com.github.suloginscene.accountant.testing.data.TestingConstants.DESCRIPTION;
import static com.github.suloginscene.accountant.testing.data.TestingConstants.MONEY_ONE;
import static com.github.suloginscene.accountant.testing.data.TestingAccountFactory.asset;
import static com.github.suloginscene.accountant.testing.data.TestingAccountFactory.expense;
import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("거래 도메인 서비스")
class TransactionServiceTest {

    @Test
    @DisplayName("복식 거래 이벤트 반환")
    void execute_onSuccess_returnsDoubleTransactionEvent() {
        TransactionService transactionService = TransactionServiceFactory.create(PURCHASE_BY_CASH);

        AccountPair pair = AccountPair.of(asset(), expense());
        TransactionExecutionParameter param = new TransactionExecutionParameter(pair, MONEY_ONE, DESCRIPTION);
        TransactionExecutedEvent event = transactionService.execute(param);

        assertThat(event).isNotNull();
    }

}
