package com.github.suloginscene.accountant.context.account.application;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.account.domain.account.AccountRepository;
import com.github.suloginscene.accountant.context.common.exception.ForbiddenException;
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
