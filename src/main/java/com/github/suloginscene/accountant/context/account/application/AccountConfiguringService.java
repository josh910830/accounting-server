package com.github.suloginscene.accountant.context.account.application;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.account.domain.account.AccountRepository;
import com.github.suloginscene.accountant.context.account.domain.account.Flow;
import com.github.suloginscene.accountant.context.account.domain.account.Stock;
import com.github.suloginscene.accountant.context.common.exception.RequestException;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.github.suloginscene.accountant.context.account.domain.account.concrete.AccountCastUtils.toFlow;
import static com.github.suloginscene.accountant.context.account.domain.account.concrete.AccountCastUtils.toStock;


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
        checkDeletable(account);
        accountRepository.deleteById(id);
    }


    private void checkDeletable(Account account) {
        if (!isDeletable(account)) {
            throw new RequestException("account is not deletable");
        }
    }

    private boolean isDeletable(Account account) {
        if (account instanceof Flow) return true;

        Stock stock = toStock(account);
        return stock.hasEmptyBalance();
    }

}
