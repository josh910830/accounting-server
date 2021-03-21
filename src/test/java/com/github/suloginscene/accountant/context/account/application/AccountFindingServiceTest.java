package com.github.suloginscene.accountant.context.account.application;

import com.github.suloginscene.accountant.context.account.domain.account.concrete.Asset;
import com.github.suloginscene.accountant.testing.base.IntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.github.suloginscene.accountant.testing.data.TestingAccountFactory.asset;
import static com.github.suloginscene.accountant.testing.data.TestingAccountFactory.expense;
import static com.github.suloginscene.accountant.testing.data.TestingAccountFactory.liability;
import static com.github.suloginscene.accountant.testing.data.TestingAccountFactory.revenue;
import static com.github.suloginscene.accountant.testing.data.TestingValues.DESCRIPTION;
import static com.github.suloginscene.accountant.testing.data.TestingValues.MONEY_ONE;
import static com.github.suloginscene.accountant.testing.data.TestingValues.TESTING_HOLDER;
import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("계정 조회 서비스")
class AccountFindingServiceTest extends IntegrationTest {

    @Autowired AccountFindingService accountFindingService;


    @Test
    @DisplayName("리스트 - 단순 계정 데이터 리스트 반환")
    void findList_onSuccess_returnsDataList() {
        repositoryFacade.given(asset(), liability(), revenue(), expense());

        List<AccountSimpleData> accounts = accountFindingService.findAccounts(TESTING_HOLDER);

        assertThat(accounts).hasSize(4);
    }

    @Test
    @DisplayName("계정 - 계정 데이터 반환")
    void findOne_onSuccess_returnsData() {
        Asset asset = asset();
        asset.increase(MONEY_ONE, DESCRIPTION);
        repositoryFacade.given(asset);

        AccountData account = accountFindingService.findAccount(asset.getId());

        assertThat(account.getSingleTransactions()).hasSize(1);
    }

}
