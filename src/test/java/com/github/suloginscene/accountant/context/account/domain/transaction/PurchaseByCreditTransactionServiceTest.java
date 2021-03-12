package com.github.suloginscene.accountant.context.account.domain.transaction;

import com.github.suloginscene.accountant.context.account.domain.account.Expense;
import com.github.suloginscene.accountant.context.account.domain.account.Liability;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import com.github.suloginscene.accountant.testing.fixture.DefaultAccounts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.github.suloginscene.accountant.context.account.domain.transaction.TransactionType.PURCHASE_BY_CREDIT;
import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("거래 도메인 서비스(신용구매)")
class PurchaseByCreditTransactionServiceTest {

    TransactionService purchaseByCredit;

    Liability liability;
    Expense expense;

    Money amount;
    String description;


    @BeforeEach
    void setup() {
        purchaseByCredit = TransactionService.create(PURCHASE_BY_CREDIT);

        liability = DefaultAccounts.liability(1);
        expense = DefaultAccounts.expense();

        amount = Money.of(1);
        description = "설명";
    }


    @Test
    @DisplayName("정상 - 복식 기록 반환")
    void purchaseByCredit_onSuccess_returnsDoubleTransaction() {
        DoubleTransaction doubleTransaction = purchaseByCredit.execute(liability, expense, amount, description);

        assertThat(doubleTransaction).isNotNull();
    }

    @Test
    @DisplayName("정상 - 부채 증가")
    void purchaseByCredit_onSuccess_increaseLiability() {
        purchaseByCredit.execute(liability, expense, amount, description);

        assertThat(liability.getBalance().get()).isEqualTo(2);
    }

}
