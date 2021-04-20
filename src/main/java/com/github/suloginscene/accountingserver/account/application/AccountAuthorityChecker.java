package com.github.suloginscene.accountingserver.account.application;

import com.github.suloginscene.accountingserver.account.domain.Account;
import com.github.suloginscene.accountingserver.account.domain.AccountRepository;
import com.github.suloginscene.exception.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountAuthorityChecker {

    private final AccountRepository accountRepository;


    public void checkAuthority(Long accountId, Long memberId) {
        Account account = accountRepository.findById(accountId);
        if (!accountIsOwnedByMember(account, memberId)) {
            throw new ForbiddenException(account, memberId);
        }
    }

    private boolean accountIsOwnedByMember(Account account, Long memberId) {
        Long holderId = account.getHolder().get();
        return holderId.equals(memberId);
    }

}
