package com.github.suloginscene.accountant.account.domain;

import com.github.suloginscene.time.TimeRange;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static com.github.suloginscene.accountant.account.domain.SingleTransactionType.INCREASE;
import static com.github.suloginscene.accountant.testing.data.TestingAccountFactory.asset;
import static com.github.suloginscene.accountant.testing.data.TestingConstants.DESCRIPTION;
import static com.github.suloginscene.accountant.testing.data.TestingConstants.MONEY_ONE;
import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("계정")
class AccountTest {

    @Test
    @DisplayName("단식 거래 기록 - 기록 추가")
    void writeSingleTransaction_onSuccess_changeState() {
        Account account = asset();

        account.writeSingleTransaction(createSingleTransaction());

        assertThat(account.readSingleTransactions()).hasSize(1);
    }

    @Test
    @DisplayName("단식 거래 조회 - 사본 반환")
    void readSingleTransactions_onSuccess_returnsCloneList() {
        Account account = asset();

        List<SingleTransaction> transactions1 = account.readSingleTransactions();
        List<SingleTransaction> transactions2 = account.readSingleTransactions();

        assertThat(transactions1).isNotSameAs(transactions2);
    }

    @Test
    @DisplayName("기간 내 거래 조회 - 필터")
    void readSingleTransactionsDuringParams_onSuccess_returnsFilteredList() {
        Account account = asset();

        account.writeSingleTransaction(createSingleTransaction());
        LocalDateTime begin = LocalDateTime.now();
        account.writeSingleTransaction(createSingleTransaction());
        LocalDateTime end = LocalDateTime.now();
        account.writeSingleTransaction(createSingleTransaction());

        TimeRange timeRange = TimeRange.of(begin, end);
        List<SingleTransaction> transactions = account.readSingleTransactions(timeRange);

        assertThat(transactions).hasSize(1);
    }


    private SingleTransaction createSingleTransaction() {
        return new SingleTransaction(INCREASE, MONEY_ONE, DESCRIPTION);
    }

}
