package com.github.suloginscene.accountant.context.account.application;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.account.domain.account.AccountRepository;
import com.github.suloginscene.accountant.context.account.domain.transaction.DoubleTransactionExecutedEvent;
import com.github.suloginscene.accountant.context.account.domain.transaction.TransactionExecutionParameter;
import com.github.suloginscene.accountant.context.account.domain.transaction.TransactionService;
import com.github.suloginscene.accountant.context.account.domain.transaction.TransactionServiceFactory;
import com.github.suloginscene.accountant.context.common.event.AccountantEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class TransactionExecutingService {

    private final AccountRepository accountRepository;
    private final AccountantEventPublisher accountantEventPublisher;


    public void executeTransaction(TransactionExecutionData data) {
        TransactionService transactionService = toService(data);
        TransactionExecutionParameter param = toParam(data);

        DoubleTransactionExecutedEvent event = transactionService.execute(param);

        accountantEventPublisher.publish(event);
    }

    private TransactionService toService(TransactionExecutionData data) {
        return TransactionServiceFactory.create(data.getType());
    }

    private TransactionExecutionParameter toParam(TransactionExecutionData data) {
        Account from = findAccount(data.getFromAccountId());
        Account to = findAccount(data.getToAccountId());

        return new TransactionExecutionParameter(
                from, to, data.getAmount(), data.getDescription(), data.getType());
    }

    private Account findAccount(Long id) {
        return accountRepository.findById(id).orElseThrow();
    }

}
