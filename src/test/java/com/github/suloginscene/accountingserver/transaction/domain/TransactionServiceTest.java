package com.github.suloginscene.accountingserver.transaction.domain;

import com.github.suloginscene.accountingserver.account.domain.concrete.Asset;
import com.github.suloginscene.accountingserver.account.domain.concrete.Expense;
import com.github.suloginscene.accountingserver.transaction.domain.param.TransactionExecutionParameter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.github.suloginscene.accountingserver.testing.data.TestingAccountFactory.asset;
import static com.github.suloginscene.accountingserver.testing.data.TestingAccountFactory.expense;
import static com.github.suloginscene.accountingserver.testing.data.TestingConstants.DESCRIPTION;
import static com.github.suloginscene.accountingserver.testing.data.TestingConstants.MONEY_ONE;
import static com.github.suloginscene.accountingserver.transaction.domain.TransactionType.PURCHASE_BY_CASH;
import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("거래 도메인 서비스")
class TransactionServiceTest {

    @Test
    @DisplayName("복식 거래 이벤트 반환")
    void execute_onSuccess_returnsDoubleTransactionEvent() {
        Asset asset = asset(1);
        Expense expense = expense(1);

        AccountPair pair = AccountPair.of(asset, expense);
        TransactionExecutionParameter param = new TransactionExecutionParameter(pair, MONEY_ONE, DESCRIPTION);

        TransactionService transactionService = TransactionServiceFactory.create(PURCHASE_BY_CASH);
        TransactionExecutedEvent event = transactionService.execute(param);

        assertThat(event).isNotNull();
    }

}
