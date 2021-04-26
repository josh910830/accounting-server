package com.github.suloginscene.accountingserver.transaction.domain;

import com.github.suloginscene.accountingserver.account.domain.Account;
import com.github.suloginscene.accountingserver.account.domain.AccountFactory;
import com.github.suloginscene.accountingserver.account.domain.AccountType;
import com.github.suloginscene.accountingserver.account.domain.concrete.Asset;
import com.github.suloginscene.accountingserver.account.domain.concrete.Expense;
import com.github.suloginscene.accountingserver.account.domain.param.AccountCreationParameter;
import com.github.suloginscene.accountingserver.common.Holder;
import com.github.suloginscene.exception.InternalException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static com.github.suloginscene.accountingserver.account.domain.AccountType.ASSET;
import static com.github.suloginscene.accountingserver.account.domain.AccountType.EXPENSE;
import static com.github.suloginscene.accountingserver.testing.data.TestingAccountFactory.asset;
import static com.github.suloginscene.accountingserver.testing.data.TestingAccountFactory.expense;
import static com.github.suloginscene.accountingserver.testing.data.TestingConstants.ACCOUNT_NAME;
import static com.github.suloginscene.accountingserver.testing.data.TestingConstants.MONEY_ONE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DisplayName("계정 페어")
class AccountPairTest {

    @Test
    @DisplayName("정상 생성")
    void create_onSuccess_returnsAccountPair() {
        Asset source = asset();
        Expense destination = expense();
        AccountPair pair = AccountPair.of(source, destination);

        assertThat(pair).isNotNull();
    }

    @Test
    @DisplayName("계정 주인 불일치 - 서버 예외")
    void create_onHolderNotMatch_throwsException() {
        Account source = createAccount(ASSET, 1L);
        Account destination = createAccount(EXPENSE, 2L);
        Executable action = () -> AccountPair.of(source, destination);

        assertThrows(InternalException.class, action);
    }

    private Account createAccount(AccountType type, long holderId) {
        Holder holder = new Holder(holderId);
        AccountCreationParameter param = new AccountCreationParameter(type, holder, ACCOUNT_NAME, MONEY_ONE);
        return AccountFactory.create(param);
    }

}
