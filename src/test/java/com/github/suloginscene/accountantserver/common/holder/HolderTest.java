package com.github.suloginscene.accountantserver.common.holder;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class HolderTest {

    @Test
    void create_onSuccess_returnsHolder() {
        Long id = 1L;
        Holder holder = new Holder(id);

        assertThat(holder.get()).isEqualTo(id);
    }

}
