package com.github.suloginscene.accountant.context.account.domain.account;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("단식 거래")
class SingleTransactionTest {

    @Test
    @DisplayName("생성시간 확인 - 참")
    void isCreatedDuring_onSuccess_returnsTrue() {
        LocalDateTime before = LocalDateTime.now();
        SingleTransaction transaction = new SingleTransaction();
        LocalDateTime after = LocalDateTime.now();

        boolean isCreatedDuring = transaction.isCreatedDuring(before, after);

        assertThat(isCreatedDuring).isTrue();
    }

    @Test
    @DisplayName("생성시간 확인(전) - 거짓")
    void isCreatedDuring_onQueryBefore_returnsFalse() {
        LocalDateTime before1 = LocalDateTime.now();
        LocalDateTime before2 = LocalDateTime.now();
        SingleTransaction transaction = new SingleTransaction();

        boolean isCreatedDuring = transaction.isCreatedDuring(before1, before2);

        assertThat(isCreatedDuring).isFalse();
    }

    @Test
    @DisplayName("생성시간 확인(후) - 거짓")
    void isCreatedDuring_onQueryAfter_returnsFalse() {
        SingleTransaction transaction = new SingleTransaction();
        LocalDateTime after1 = LocalDateTime.now();
        LocalDateTime after2 = LocalDateTime.now();

        boolean isCreatedDuring = transaction.isCreatedDuring(after1, after2);

        assertThat(isCreatedDuring).isFalse();
    }

}
