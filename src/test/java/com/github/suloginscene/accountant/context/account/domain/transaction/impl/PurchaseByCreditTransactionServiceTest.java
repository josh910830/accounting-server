package com.github.suloginscene.accountant.context.account.domain.transaction.impl;

import com.github.suloginscene.accountant.context.account.domain.account.concrete.Expense;
import com.github.suloginscene.accountant.context.account.domain.account.concrete.Liability;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import com.github.suloginscene.accountant.testing.fixture.DefaultAccounts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("거래 도메인 서비스(신용구매)")
class PurchaseByCreditTransactionServiceTest {

    PurchaseByCreditTransactionService purchaseByCredit;

    Liability liability;
    Expense expense;

    Money amount;
    String description;


    @BeforeEach
    void setup() {
        purchaseByCredit = new PurchaseByCreditTransactionService();

        liability = DefaultAccounts.liability(1);
        expense = DefaultAccounts.expense(1);

        amount = Money.of(1);
        description = "설명";
    }


    @Test
    @DisplayName("정상 - 부채 증가")
    void purchaseByCredit_onSuccess_increaseLiability() {
        purchaseByCredit.doExecute(liability, expense, amount, description);

        assertThat(liability.getBalance().get()).isEqualTo(2);
    }

}
