package com.github.suloginscene.accountant.context.account.application;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.account.domain.account.AccountRepository;
import com.github.suloginscene.accountant.context.account.domain.account.Flow;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.github.suloginscene.accountant.context.account.domain.account.concrete.AccountCastUtils.toFlow;


@Service
@Transactional
@RequiredArgsConstructor
public class AccountConfiguringService {

    private final AccountRepository accountRepository;


    public void changeName(Long id, String newName) {
        Account account = findAccount(id);
        account.changeName(newName);
    }

    public void changeBudget(Long id, Money newBudget) {
        Flow flow = toFlow(findAccount(id));
        flow.changeBudget(newBudget);
    }


    private Account findAccount(Long id) {
        return accountRepository.findById(id).orElseThrow();
    }

}
