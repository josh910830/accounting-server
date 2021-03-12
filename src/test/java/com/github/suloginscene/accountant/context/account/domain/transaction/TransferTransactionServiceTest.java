package com.github.suloginscene.accountant.context.account.domain.transaction;

import com.github.suloginscene.accountant.context.account.domain.account.Asset;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import com.github.suloginscene.accountant.context.common.value.money.NegativeMoneyException;
import com.github.suloginscene.accountant.testing.fixture.DefaultAccounts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static com.github.suloginscene.accountant.context.account.domain.transaction.TransactionType.TRANSFER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DisplayName("거래 도메인 서비스(이체)")
class TransferTransactionServiceTest {

    TransactionService transfer;

    Asset fromAsset;
    Asset toAsset;

    Money amount;
    String description;


    @BeforeEach
    void setup() {
        transfer = TransactionServiceFactory.create(TRANSFER);

        fromAsset = DefaultAccounts.asset(1);
        toAsset = DefaultAccounts.asset(1);

        amount = Money.of(1);
        description = "설명";
    }


    @Test
    @DisplayName("정상 - 자산1 감소 & 자산2 증가")
    void transfer_onSuccess_decreaseAsset1AndIncreaseAsset2() {
        transfer.doExecute(fromAsset, toAsset, amount, description);

        assertThat(fromAsset.getBalance().get()).isEqualTo(0);
        assertThat(toAsset.getBalance().get()).isEqualTo(2);
    }

    @Test
    @DisplayName("잔액 부족 - 예외 발생")
    void transfer_onInsufficientBalance_throwsException() {
        Executable action = () -> transfer.doExecute(fromAsset, toAsset, Money.of(2), description);

        assertThrows(NegativeMoneyException.class, action);
    }

}
