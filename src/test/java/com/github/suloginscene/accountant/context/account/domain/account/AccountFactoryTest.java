package com.github.suloginscene.accountant.context.account.domain.account;

import com.github.suloginscene.accountant.context.account.domain.account.concrete.Asset;
import com.github.suloginscene.accountant.context.account.domain.account.concrete.Expense;
import com.github.suloginscene.accountant.context.account.domain.account.concrete.Liability;
import com.github.suloginscene.accountant.context.account.domain.account.concrete.Revenue;
import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.github.suloginscene.accountant.context.account.domain.account.AccountType.ASSET;
import static com.github.suloginscene.accountant.context.account.domain.account.AccountType.EXPENSE;
import static com.github.suloginscene.accountant.context.account.domain.account.AccountType.LIABILITY;
import static com.github.suloginscene.accountant.context.account.domain.account.AccountType.REVENUE;
import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("계정 팩토리")
class AccountFactoryTest {

    @Test
    @DisplayName("자산 생성")
    void createAsset() {
        AccountCreationParameter param = createParam(ASSET);
        Account asset = AccountFactory.create(param);

        assertThat(asset).isInstanceOf(Asset.class);
    }

    @Test
    @DisplayName("부채 생성")
    void createLiability() {
        AccountCreationParameter param = createParam(LIABILITY);
        Account liability = AccountFactory.create(param);

        assertThat(liability).isInstanceOf(Liability.class);
    }

    @Test
    @DisplayName("수입 생성")
    void createRevenue() {
        AccountCreationParameter param = createParam(REVENUE);
        Account revenue = AccountFactory.create(param);

        assertThat(revenue).isInstanceOf(Revenue.class);
    }

    @Test
    @DisplayName("지출 생성")
    void createExpense() {
        AccountCreationParameter param = createParam(EXPENSE);
        Account expense = AccountFactory.create(param);

        assertThat(expense).isInstanceOf(Expense.class);
    }

    private AccountCreationParameter createParam(AccountType type) {
        Holder holder = new Holder(1L);
        String name = "계정명";
        Money money = Money.of(1);
        return new AccountCreationParameter(type, holder, name, money);
    }

}
