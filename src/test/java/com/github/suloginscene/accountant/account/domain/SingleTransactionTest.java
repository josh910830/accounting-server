package com.github.suloginscene.accountant.account.domain;

import com.github.suloginscene.time.TimeRange;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("단식 거래")
class SingleTransactionTest {

    @Test
    @DisplayName("생성시간 확인 - 참")
    void isCreatedDuring_onSuccess_returnsTrue() {
        LocalDateTime begin = now();
        SingleTransaction transaction = new SingleTransaction();
        LocalDateTime end = now();

        TimeRange timeRange = TimeRange.of(begin, end);
        boolean isCreatedDuring = transaction.isCreatedDuring(timeRange);

        assertThat(isCreatedDuring).isTrue();
    }

    @Test
    @DisplayName("생성시간 확인(전) - 거짓")
    void isCreatedDuring_onQueryBefore_returnsFalse() {
        LocalDateTime begin = now();
        LocalDateTime end = now();
        SingleTransaction transaction = new SingleTransaction();

        TimeRange timeRange = TimeRange.of(begin, end);
        boolean isCreatedDuring = transaction.isCreatedDuring(timeRange);

        assertThat(isCreatedDuring).isFalse();
    }

    @Test
    @DisplayName("생성시간 확인(후) - 거짓")
    void isCreatedDuring_onQueryAfter_returnsFalse() {
        SingleTransaction transaction = new SingleTransaction();
        LocalDateTime begin = now();
        LocalDateTime end = now();

        TimeRange timeRange = TimeRange.of(begin, end);
        boolean isCreatedDuring = transaction.isCreatedDuring(timeRange);

        assertThat(isCreatedDuring).isFalse();
    }

}
