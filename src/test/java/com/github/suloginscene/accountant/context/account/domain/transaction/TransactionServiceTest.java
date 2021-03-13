package com.github.suloginscene.accountant.context.account.domain.transaction;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.account.domain.account.AccountCreationParameter;
import com.github.suloginscene.accountant.context.account.domain.account.AccountFactory;
import com.github.suloginscene.accountant.context.account.domain.account.AccountType;
import com.github.suloginscene.accountant.context.common.event.DoubleTransactionExecutedEvent;
import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static com.github.suloginscene.accountant.context.account.domain.account.AccountType.ASSET;
import static com.github.suloginscene.accountant.context.account.domain.account.AccountType.EXPENSE;
import static com.github.suloginscene.accountant.context.account.domain.transaction.TransactionType.PURCHASE_BY_CASH;
import static com.github.suloginscene.accountant.testing.fixture.DefaultAccounts.asset;
import static com.github.suloginscene.accountant.testing.fixture.DefaultAccounts.expense;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DisplayName("거래 도메인 서비스")
class TransactionServiceTest {

    TransactionService transactionService;

    TransactionExecutionParameter param;


    @BeforeEach
    void setup() {
        TransactionType transactionType = PURCHASE_BY_CASH;
        transactionService = TransactionServiceFactory.create(transactionType);

        Account from = asset(1);
        Account to = expense();
        Money amount = Money.of(1);
        String description = "설명";
        param = new TransactionExecutionParameter(from, to, amount, description, transactionType);
    }


    @Test
    @DisplayName("복식 거래 이벤트 반환")
    void execute_onSuccess_returnsDoubleTransactionEvent() {
        DoubleTransactionExecutedEvent event = transactionService.execute(param);

        assertThat(event).isNotNull();
    }

    @Test
    @DisplayName("계정 주인 불일치 - 예외 발생")
    void execute_onHolderNotMatch_throwsException() {
        Account from = createAccount(ASSET, 1L);
        Account to = createAccount(EXPENSE, 2L);

        param = new TransactionExecutionParameter(from, to, Money.of(1), "설명", PURCHASE_BY_CASH);
        Executable action = () -> transactionService.execute(param);

        assertThrows(HolderNotMatchedException.class, action);
    }

    private Account createAccount(AccountType type, long holderId) {
        Holder holder = new Holder(holderId);
        AccountCreationParameter param = new AccountCreationParameter(type, holder, "계정명", Money.of(1));
        return AccountFactory.create(param);
    }

}
