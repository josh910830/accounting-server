package com.github.suloginscene.accountant.context.account.domain.account;

import com.github.suloginscene.accountant.context.common.value.money.Money;
import com.github.suloginscene.accountant.testing.fixture.DefaultAccounts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("계정")
class AccountTest {

    Account account;

    SingleTransactionType type;
    Money amount;
    String description;


    @BeforeEach
    void setup() {
        account = DefaultAccounts.asset(1);

        type = SingleTransactionType.INCREASE;
        amount = Money.of(1);
        description = "설명";
    }


    @Test
    @DisplayName("단식 거래 기록 - 기록 추가")
    void writeSingleTransaction_onSuccess_changeState() {
        account.writeSingleTransaction(new SingleTransaction(type, amount, description));

        assertThat(account.readSingleTransactions()).hasSize(1);
    }

    @Test
    @DisplayName("단식 거래 조회 - 사본 반환")
    void readSingleTransactions_onSuccess_returnsCloneList() {
        List<SingleTransaction> transactions1 = account.readSingleTransactions();
        List<SingleTransaction> transactions2 = account.readSingleTransactions();

        assertThat(transactions1).isNotSameAs(transactions2);
    }

    @Test
    @DisplayName("기간 내 거래 조회 - 필터")
    void readSingleTransactionsDuringParams_onSuccess_returnsFilteredList() {
        account.writeSingleTransaction(new SingleTransaction(type, amount, description));
        LocalDateTime from = LocalDateTime.now();
        account.writeSingleTransaction(new SingleTransaction(type, amount, description));
        LocalDateTime to = LocalDateTime.now();
        account.writeSingleTransaction(new SingleTransaction(type, amount, description));

        List<SingleTransaction> transactions = account.readSingleTransactions(from, to);

        assertThat(transactions).hasSize(1);
    }

}
