package com.github.suloginscene.accountant.context.account.application;

import com.github.suloginscene.accountant.testing.base.IntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.github.suloginscene.accountant.testing.data.TestingValues.TESTING_HOLDER;
import static com.github.suloginscene.accountant.testing.data.TestingAccountFactory.asset;
import static com.github.suloginscene.accountant.testing.data.TestingAccountFactory.expense;
import static com.github.suloginscene.accountant.testing.data.TestingAccountFactory.liability;
import static com.github.suloginscene.accountant.testing.data.TestingAccountFactory.revenue;
import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("계정 조회 서비스")
class AccountFindingServiceTest extends IntegrationTest {

    @Autowired AccountFindingService accountFindingService;


    @Test
    @DisplayName("정상 - 단순 계정 데이터 리스트 반환")
    void find_onSuccess_returnsList() {
        repositoryFacade.given(asset(), liability(), revenue(), expense());

        List<AccountSimpleData> accounts = accountFindingService.findAccounts(TESTING_HOLDER);

        assertThat(accounts).hasSize(4);
    }

}
