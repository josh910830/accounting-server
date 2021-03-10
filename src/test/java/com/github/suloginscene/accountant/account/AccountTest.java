package com.github.suloginscene.accountant.account;

import com.github.suloginscene.accountant.common.holder.Holder;
import com.github.suloginscene.accountant.common.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.github.suloginscene.accountant.account.AccountType.ASSET;
import static com.github.suloginscene.accountant.account.AccountType.EXPENSE;
import static com.github.suloginscene.accountant.account.AccountType.LIABILITY;
import static com.github.suloginscene.accountant.account.AccountType.REVENUE;
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
        AccountCreationParameter param = createParam(ASSET, "하나 자유");
        Account asset = Account.create(param);

        assertThat(asset).isInstanceOf(Asset.class);
    }

    @Test
    @DisplayName("부채 생성")
    void createLiability_onSuccess_returnsLiability() {
        AccountCreationParameter param = createParam(LIABILITY, "현대 신용");
        Account liability = Account.create(param);

        assertThat(liability).isInstanceOf(Liability.class);
    }

    @Test
    @DisplayName("수입 생성")
    void createRevenue_onSuccess_returnsRevenue() {
        AccountCreationParameter param = createParam(REVENUE, "월급");
        Account revenue = Account.create(param);

        assertThat(revenue).isInstanceOf(Revenue.class);
    }

    @Test
    @DisplayName("지출 생성")
    void createExpense_onSuccess_returnsExpense() {
        AccountCreationParameter param = createParam(EXPENSE, "식비");
        Account expense = Account.create(param);

        assertThat(expense).isInstanceOf(Expense.class);
    }

    private AccountCreationParameter createParam(AccountType type, String name) {
        return new AccountCreationParameter(type, holder, name, Money.of(1));
    }

}
