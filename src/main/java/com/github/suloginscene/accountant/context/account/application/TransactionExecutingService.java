package com.github.suloginscene.accountant.context.account.application;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.account.domain.account.AccountRepository;
import com.github.suloginscene.accountant.context.account.domain.transaction.AccountPair;
import com.github.suloginscene.accountant.context.account.domain.transaction.TransactionExecutedEvent;
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
        TransactionService transaction = toTransaction(data);
        TransactionExecutionParameter param = toParam(data);

        TransactionExecutedEvent event = transaction.execute(param);

        accountantEventPublisher.publish(event);
    }

    private TransactionService toTransaction(TransactionExecutionData data) {
        return TransactionServiceFactory.create(data.getType());
    }

    private TransactionExecutionParameter toParam(TransactionExecutionData data) {
        Account source = findAccount(data.getSourceId());
        Account destination = findAccount(data.getDestinationId());
        AccountPair pair = AccountPair.of(source, destination);
        return new TransactionExecutionParameter(pair, data.getAmount(), data.getDescription());
    }

    private Account findAccount(Long id) {
        return accountRepository.findById(id).orElseThrow();
    }

}
