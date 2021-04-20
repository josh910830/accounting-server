package com.github.suloginscene.accountingserver.account.domain;

import com.github.suloginscene.time.TimeRange;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static com.github.suloginscene.accountingserver.account.domain.SingleTransactionType.INCREASE;
import static com.github.suloginscene.accountingserver.testing.data.TestingAccountFactory.asset;
import static com.github.suloginscene.accountingserver.testing.data.TestingConstants.DESCRIPTION;
import static com.github.suloginscene.accountingserver.testing.data.TestingConstants.MONEY_ONE;
import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("계정")
class AccountTest {

    @Test
    @DisplayName("단식 거래 기록 - 기록 추가")
    void writeSingleTransaction_onSuccess_changeState() {
        Account account = asset();

        SingleTransaction transaction = new SingleTransaction(account, INCREASE, MONEY_ONE, DESCRIPTION);
        account.writeSingleTransaction(transaction);

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

        SingleTransaction t1 = new SingleTransaction(account, INCREASE, MONEY_ONE, DESCRIPTION);
        LocalDateTime begin = LocalDateTime.now();
        SingleTransaction t2 = new SingleTransaction(account, INCREASE, MONEY_ONE, DESCRIPTION);
        LocalDateTime end = LocalDateTime.now();
        SingleTransaction t3 = new SingleTransaction(account, INCREASE, MONEY_ONE, DESCRIPTION);

        account.writeSingleTransaction(t1);
        account.writeSingleTransaction(t2);
        account.writeSingleTransaction(t3);

        TimeRange timeRange = TimeRange.of(begin, end);
        List<SingleTransaction> transactions = account.readSingleTransactions(timeRange);

        assertThat(transactions).hasSize(1);
    }

}
