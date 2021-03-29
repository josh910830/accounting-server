package com.github.suloginscene.accountant.transaction.domain.impl;

import com.github.suloginscene.accountant.account.domain.concrete.Asset;
import com.github.suloginscene.accountant.common.Money;
import com.github.suloginscene.exception.RequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static com.github.suloginscene.accountant.testing.data.TestingAccountFactory.asset;
import static com.github.suloginscene.accountant.testing.data.TestingConstants.DESCRIPTION;
import static com.github.suloginscene.accountant.testing.data.TestingConstants.MONEY_ONE;
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
    @DisplayName("잔액 부족 - 요청 예외")
    void transfer_onInsufficientBalance_throwsException() {
        Asset sourceAsset = asset(1);
        Asset destinationAsset = asset(1);

        TransferTransactionService transfer = new TransferTransactionService();
        Executable action = () -> transfer.doExecute(sourceAsset, destinationAsset, Money.of(2), DESCRIPTION);

        assertThrows(RequestException.class, action);
    }

}
