package com.github.suloginscene.accountant.transaction.domain.impl;

import com.github.suloginscene.accountant.account.domain.concrete.Asset;
import com.github.suloginscene.accountant.account.domain.concrete.Liability;
import com.github.suloginscene.accountant.common.Money;
import com.github.suloginscene.lib.exception.RequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static com.github.suloginscene.accountant.testing.data.TestingAccountFactory.asset;
import static com.github.suloginscene.accountant.testing.data.TestingAccountFactory.liability;
import static com.github.suloginscene.accountant.testing.data.TestingConstants.DESCRIPTION;
import static com.github.suloginscene.accountant.testing.data.TestingConstants.MONEY_ONE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DisplayName("거래 도메인 서비스(상환)")
class RepayTransactionServiceTest {

    @Test
    @DisplayName("정상 - 자산 감소 & 부채 감소")
    void repay_onSuccess_decreaseAssetAndDecreaseLiability() {
        Asset asset = asset(1);
        Liability liability = liability(1);

        RepayTransactionService repay = new RepayTransactionService();
        repay.doExecute(asset, liability, MONEY_ONE, DESCRIPTION);

        assertThat(asset.getBalance().get()).isEqualTo(0);
        assertThat(liability.getBalance().get()).isEqualTo(0);
    }

    @Test
    @DisplayName("잔액 부족 - 요청 예외")
    void repay_onInsufficientBalance_throwsException() {
        Asset asset = asset(1);
        Liability liability = liability(10);

        RepayTransactionService repay = new RepayTransactionService();
        Executable action = () -> repay.doExecute(asset, liability, Money.of(2), DESCRIPTION);

        assertThrows(RequestException.class, action);
    }

    @Test
    @DisplayName("초과 상환 - 요청 예외")
    void repay_onOverRepay_throwsException() {
        Asset asset = asset(10);
        Liability liability = liability(1);

        RepayTransactionService repay = new RepayTransactionService();
        Executable action = () -> repay.doExecute(asset, liability, Money.of(2), DESCRIPTION);

        assertThrows(RequestException.class, action);
    }

}
