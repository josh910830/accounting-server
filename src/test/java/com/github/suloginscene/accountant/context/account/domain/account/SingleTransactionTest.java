package com.github.suloginscene.accountant.context.account.domain.account;

import com.github.suloginscene.accountant.context.common.value.range.TimeRange;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("단식 거래")
class SingleTransactionTest {

    @Test
    @DisplayName("생성시간 확인 - 참")
    void isCreatedDuring_onSuccess_returnsTrue() {
        LocalDateTime begin = LocalDateTime.now();
        SingleTransaction transaction = new SingleTransaction();
        LocalDateTime end = LocalDateTime.now();

        TimeRange timeRange = TimeRange.of(begin, end);
        boolean isCreatedDuring = transaction.isCreatedDuring(timeRange);

        assertThat(isCreatedDuring).isTrue();
    }

    @Test
    @DisplayName("생성시간 확인(전) - 거짓")
    void isCreatedDuring_onQueryBefore_returnsFalse() {
        LocalDateTime begin = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now();
        SingleTransaction transaction = new SingleTransaction();

        TimeRange timeRange = TimeRange.of(begin, end);
        boolean isCreatedDuring = transaction.isCreatedDuring(timeRange);

        assertThat(isCreatedDuring).isFalse();
    }

    @Test
    @DisplayName("생성시간 확인(후) - 거짓")
    void isCreatedDuring_onQueryAfter_returnsFalse() {
        SingleTransaction transaction = new SingleTransaction();
        LocalDateTime begin = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now();

        TimeRange timeRange = TimeRange.of(begin, end);
        boolean isCreatedDuring = transaction.isCreatedDuring(timeRange);

        assertThat(isCreatedDuring).isFalse();
    }

}
