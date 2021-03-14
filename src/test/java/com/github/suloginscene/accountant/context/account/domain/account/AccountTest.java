package com.github.suloginscene.accountant.context.account.domain.account;

import com.github.suloginscene.accountant.context.common.value.money.Money;
import com.github.suloginscene.accountant.testing.fixture.DefaultAccounts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.spy;


@DisplayName("계정")
class AccountTest {

    Account account;
    SingleTransaction singleTransaction;


    @BeforeEach
    void setup() {
        account = DefaultAccounts.asset(1);

        SingleTransactionType type = SingleTransactionType.INCREASE;
        Money amount = Money.of(1);
        String description = "설명";
        singleTransaction = new SingleTransaction(type, amount, description);
    }


    @Test
    @DisplayName("단식 거래 기록 - 기록 추가")
    void writeSingleTransaction_onSuccess_changeState() {
        account.writeSingleTransaction(singleTransaction);

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
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime before = now.minusHours(1);
        LocalDateTime mid = now.minusSeconds(1);
        LocalDateTime after = now.plusHours(1);

        writeMockTransaction(before);
        writeMockTransaction(mid);
        writeMockTransaction(after);

        List<SingleTransaction> transactions = account.readSingleTransactions(before, now);

        assertThat(transactions).hasSize(2);
    }

    private void writeMockTransaction(LocalDateTime createdAt) {
        SingleTransaction transaction = spy(SingleTransaction.class);
        given(transaction.getCreatedAt()).willReturn(createdAt);

        account.writeSingleTransaction(transaction);
    }

}
