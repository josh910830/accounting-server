package com.github.suloginscene.accountant.testing.base;

import com.github.suloginscene.accountant.context.common.event.AccountantEventPublisher;
import com.github.suloginscene.accountant.testing.db.RepositoryFacade;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;


@SpringBootTest
@Slf4j
public abstract class IntegrationTest {

    @Autowired protected RepositoryFacade repositoryFacade;

    @SpyBean protected AccountantEventPublisher accountantEventPublisher;


    @AfterEach
    final void clearAllRepositories() {
        log.info("clear all repositories");
        repositoryFacade.clear();
    }

}
