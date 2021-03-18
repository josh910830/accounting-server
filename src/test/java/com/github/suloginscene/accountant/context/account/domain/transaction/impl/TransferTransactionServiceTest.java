package com.github.suloginscene.accountant.context.account.domain.transaction.impl;

import com.github.suloginscene.accountant.context.account.domain.account.concrete.Asset;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import com.github.suloginscene.accountant.context.common.value.money.NegativeMoneyException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static com.github.suloginscene.accountant.testing.data.TestingAccountFactory.asset;
import static com.github.suloginscene.accountant.testing.data.TestingValues.MONEY_ONE;
import static com.github.suloginscene.accountant.testing.data.TestingValues.DESCRIPTION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DisplayName("거래 도메인 서비스(이체)")
class TransferTransactionServiceTest {

    @Test
    @DisplayName("정상 - 자산1 감소 & 자산2 증가")
    void transfer_onSuccess_decreaseAsset1AndIncreaseAsset2() {
        Asset sourceAsset = asset(1);
        Asset destinationAsset = asset(1);

        TransferTransactionService transfer = new TransferTransactionService();
        transfer.doExecute(sourceAsset, destinationAsset, MONEY_ONE, DESCRIPTION);

        assertThat(sourceAsset.getBalance().get()).isEqualTo(0);
        assertThat(destinationAsset.getBalance().get()).isEqualTo(2);
    }

    @Test
    @DisplayName("잔액 부족 - 예외 발생")
    void transfer_onInsufficientBalance_throwsException() {
        Asset sourceAsset = asset(1);
        Asset destinationAsset = asset(1);

        TransferTransactionService transfer = new TransferTransactionService();
        Executable action = () -> transfer.doExecute(sourceAsset, destinationAsset, Money.of(2), DESCRIPTION);

        assertThrows(NegativeMoneyException.class, action);
    }

}
