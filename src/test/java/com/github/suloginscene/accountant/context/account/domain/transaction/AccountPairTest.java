package com.github.suloginscene.accountant.context.account.domain.transaction;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.account.domain.account.AccountCreationParameter;
import com.github.suloginscene.accountant.context.account.domain.account.AccountFactory;
import com.github.suloginscene.accountant.context.account.domain.account.AccountType;
import com.github.suloginscene.accountant.context.account.domain.account.concrete.Asset;
import com.github.suloginscene.accountant.context.account.domain.account.concrete.Expense;
import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static com.github.suloginscene.accountant.context.account.domain.account.AccountType.ASSET;
import static com.github.suloginscene.accountant.context.account.domain.account.AccountType.EXPENSE;
import static com.github.suloginscene.accountant.testing.fixture.DefaultAccounts.asset;
import static com.github.suloginscene.accountant.testing.fixture.DefaultAccounts.expense;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DisplayName("계정 페어")
class AccountPairTest {

    @Test
    @DisplayName("정상 생성")
    void create_onSuccess_returnsAccountPair() {
        Asset source = asset(1);
        Expense destination = expense();
        AccountPair pair = AccountPair.of(source, destination);

        assertThat(pair).isNotNull();
    }

    @Test
    @DisplayName("계정 주인 불일치 - 예외 발생")
    void create_onHolderNotMatch_throwsException() {
        Account source = createAccount(ASSET, 1L);
        Account destination = createAccount(EXPENSE, 2L);
        Executable action = () -> AccountPair.of(source, destination);

        assertThrows(AccountPairHolderNotMatchedException.class, action);
    }

    private Account createAccount(AccountType type, long holderId) {
        Holder holder = new Holder(holderId);
        String name = "계정명";
        Money money = Money.of(1);
        AccountCreationParameter param = new AccountCreationParameter(type, holder, name, money);
        return AccountFactory.create(param);
    }

}
