package com.github.suloginscene.accountant.context.account.domain.transaction.impl;

import com.github.suloginscene.accountant.context.account.domain.account.concrete.Expense;
import com.github.suloginscene.accountant.context.account.domain.account.concrete.Liability;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.github.suloginscene.accountant.testing.data.TestingAccountFactory.expense;
import static com.github.suloginscene.accountant.testing.data.TestingAccountFactory.liability;
import static com.github.suloginscene.accountant.testing.data.TestingValues.MONEY_ONE;
import static com.github.suloginscene.accountant.testing.data.TestingValues.DESCRIPTION;
import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("거래 도메인 서비스(신용구매)")
class PurchaseByCreditTransactionServiceTest {

    @Test
    @DisplayName("정상 - 부채 증가")
    void purchaseByCredit_onSuccess_increaseLiability() {
        Liability liability = liability(1);
        Expense expense = expense(1);

        PurchaseByCreditTransactionService purchaseByCredit = new PurchaseByCreditTransactionService();
        purchaseByCredit.doExecute(liability, expense, MONEY_ONE, DESCRIPTION);

        assertThat(liability.getBalance().get()).isEqualTo(2);
    }

}
