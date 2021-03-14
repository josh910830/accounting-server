package com.github.suloginscene.accountant.context.account.domain.account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.spy;


@DisplayName("단식 거래")
class SingleTransactionTest {

    SingleTransaction transaction;

    LocalDateTime pivot;
    LocalDateTime before;
    LocalDateTime after;


    @BeforeEach
    void setup() {
        pivot = LocalDateTime.now();
        before = pivot.minusHours(1);
        after = pivot.plusHours(1);

        transaction = spy(SingleTransaction.class);
        given(transaction.getCreatedAt()).willReturn(pivot);
    }


    @Test
    @DisplayName("생성시간 확인(사이) - 참")
    void isCreatedDuring_fromBeforeToAfter_returnsTrue() {
        boolean isCreatedDuring = transaction.isCreatedDuring(before, after);

        assertThat(isCreatedDuring).isTrue();
    }

    @Test
    @DisplayName("생성시간 확인(좌극) - 참")
    void isCreatedDuring_fromPivotToAfter_returnsTrue() {
        boolean isCreatedDuring = transaction.isCreatedDuring(pivot, after);

        assertThat(isCreatedDuring).isTrue();
    }

    @Test
    @DisplayName("생성시간 확인(우극) - 거짓")
    void isCreatedDuring_fromBeforeToPivot_returnsFalse() {
        boolean isCreatedDuring = transaction.isCreatedDuring(before, pivot);

        assertThat(isCreatedDuring).isFalse();
    }

}
