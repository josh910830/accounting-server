package com.github.suloginscene.accountant.account.application;

import com.github.suloginscene.accountant.account.domain.Account;
import com.github.suloginscene.accountant.account.domain.AccountCreationParameter;
import com.github.suloginscene.accountant.account.domain.AccountFactory;
import com.github.suloginscene.accountant.account.domain.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class AccountCreatingService {

    private final AccountRepository accountRepository;


    public Long createAccount(AccountCreationInput input) {
        AccountCreationParameter param = toParam(input);

        Account newAccount = AccountFactory.create(param);
        return accountRepository.save(newAccount);
    }

    private AccountCreationParameter toParam(AccountCreationInput input) {
        return new AccountCreationParameter(
                input.getType(), input.getHolder(), input.getName(), input.getMoney());
    }

}
