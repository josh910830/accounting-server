package com.github.suloginscene.accountant.account.application;

import com.github.suloginscene.accountant.account.domain.Account;
import com.github.suloginscene.accountant.account.domain.AccountRepository;
import com.github.suloginscene.accountant.account.domain.Flow;
import com.github.suloginscene.accountant.account.domain.Stock;
import com.github.suloginscene.accountant.common.Money;
import com.github.suloginscene.accountant.lib.exception.RequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.github.suloginscene.accountant.account.domain.concrete.AccountCastUtils.toFlow;
import static com.github.suloginscene.accountant.account.domain.concrete.AccountCastUtils.toStock;


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
