package com.github.suloginscene.accountant.account;

import com.github.suloginscene.accountant.common.holder.Holder;
import com.github.suloginscene.accountant.common.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("계정")
class AccountTest {

    Holder holder;


    @BeforeEach
    void setup() {
        holder = new Holder(1L);
    }


    @Test
    @DisplayName("자산 생성")
    void createAsset_onSuccess_returnsAsset() {
        String name = "하나 자유";
        Money balance = Money.of(1);
        Account asset = Account.createAsset(holder, name, balance);

        assertThat(asset).isInstanceOf(Asset.class);
    }

    @Test
    @DisplayName("부채 생성")
    void createLiability_onSuccess_returnsLiability() {
        String name = "현대 신용";
        Money balance = Money.of(1);
        Account liability = Account.createLiability(holder, name, balance);

        assertThat(liability).isInstanceOf(Liability.class);
    }

    @Test
    @DisplayName("수입 생성")
    void createRevenue_onSuccess_returnsRevenue() {
        String name = "월급";
        Money budget = Money.of(1);
        Account revenue = Account.createRevenue(holder, name, budget);

        assertThat(revenue).isInstanceOf(Revenue.class);
    }

    @Test
    @DisplayName("지출 생성")
    void createExpense_onSuccess_returnsExpense() {
        String name = "식비";
        Money budget = Money.of(1);
        Account expense = Account.createExpense(holder, name, budget);

        assertThat(expense).isInstanceOf(Expense.class);
    }

}
