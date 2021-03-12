package com.github.suloginscene.accountant.context.account.domain.account;

import com.github.suloginscene.accountant.testing.fixture.DefaultAccounts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;


@DisplayName("계정(유량)")
class FlowTest {

    Flow flow;


    @BeforeEach
    void setup() {
        flow = DefaultAccounts.revenue();
    }

}
