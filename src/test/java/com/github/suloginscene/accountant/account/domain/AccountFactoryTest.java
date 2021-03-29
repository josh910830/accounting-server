package com.github.suloginscene.accountant.account.domain;

import com.github.suloginscene.accountant.account.domain.concrete.Asset;
import com.github.suloginscene.accountant.account.domain.concrete.Expense;
import com.github.suloginscene.accountant.account.domain.concrete.Liability;
import com.github.suloginscene.accountant.account.domain.concrete.Revenue;
import com.github.suloginscene.accountant.account.domain.param.AccountCreationParameter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.github.suloginscene.accountant.account.domain.AccountType.ASSET;
import static com.github.suloginscene.accountant.account.domain.AccountType.EXPENSE;
import static com.github.suloginscene.accountant.account.domain.AccountType.LIABILITY;
import static com.github.suloginscene.accountant.account.domain.AccountType.REVENUE;
import static com.github.suloginscene.accountant.testing.data.TestingConstants.ACCOUNT_NAME;
import static com.github.suloginscene.accountant.testing.data.TestingConstants.MONEY_ONE;
import static com.github.suloginscene.accountant.testing.data.TestingConstants.TESTING_HOLDER;
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
        return new AccountCreationParameter(type, TESTING_HOLDER, ACCOUNT_NAME, MONEY_ONE);
    }

}
