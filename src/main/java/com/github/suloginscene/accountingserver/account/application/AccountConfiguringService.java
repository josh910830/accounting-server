package com.github.suloginscene.accountingserver.account.application;

import com.github.suloginscene.accountingserver.account.domain.Account;
import com.github.suloginscene.accountingserver.account.domain.AccountRepository;
import com.github.suloginscene.accountingserver.account.domain.Flow;
import com.github.suloginscene.accountingserver.common.Holder;
import com.github.suloginscene.accountingserver.common.Money;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.github.suloginscene.accountingserver.account.domain.concrete.AccountCastUtils.toFlow;


@Service
@Transactional
@RequiredArgsConstructor
public class AccountConfiguringService {

    private final AccountRepository accountRepository;


    public void changeName(Long id, String newName) {
        Account account = accountRepository.findById(id);
        account.changeName(newName);
    }

    public void changeBudget(Long id, Money newBudget) {
        Account account = accountRepository.findById(id);
        Flow flow = toFlow(account);
        flow.changeBudget(newBudget);
    }


    public void delete(Long id) {
        Account account = accountRepository.findById(id);
        accountRepository.deleteWithChildren(account);
    }

    public void deleteByHolderForce(Holder holder) {
        accountRepository.deleteByHolderWithChildren(holder);
    }

}
