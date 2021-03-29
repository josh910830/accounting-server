package com.github.suloginscene.accountant.account.application;

import com.github.suloginscene.accountant.account.domain.concrete.Asset;
import com.github.suloginscene.accountant.testing.base.IntegrationTest;
import com.github.suloginscene.exception.ForbiddenException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;

import static com.github.suloginscene.accountant.testing.data.TestingAccountFactory.asset;
import static com.github.suloginscene.accountant.testing.data.TestingConstants.TESTING_HOLDER;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DisplayName("권한 확인 컴포넌트")
class AccountAuthorityCheckerTest extends IntegrationTest {

    @Autowired AccountAuthorityChecker accountAuthorityChecker;


    @Test
    @DisplayName("정상 - 예외 미발생")
    void checkAuthority_onSuccess_doesNotThrow() {
        Asset asset = asset();
        given(asset);

        Executable action = () -> accountAuthorityChecker.checkAuthority(asset.getId(), TESTING_HOLDER.get());

        assertDoesNotThrow(action);
    }

    @Test
    @DisplayName("다른 사용자 - 예외 발생")
    void checkAuthority_onNotOwner_throwsException() {
        Asset asset = asset();
        given(asset);

        Executable action = () -> accountAuthorityChecker.checkAuthority(asset.getId(), Long.MAX_VALUE);

        assertThrows(ForbiddenException.class, action);
    }

}
