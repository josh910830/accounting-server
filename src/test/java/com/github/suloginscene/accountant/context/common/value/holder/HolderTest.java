package com.github.suloginscene.accountant.context.common.value.holder;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("소유자")
class HolderTest {

    @Test
    @DisplayName("생성 정상 - ID 보유")
    void create_onSuccess_returnsHolder() {
        Long id = 1L;
        Holder holder = new Holder(id);

        assertThat(holder.get()).isEqualTo(id);
    }

}
