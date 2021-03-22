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


    public void executeTransaction(TransactionExecutionInput input) {
        TransactionService transaction = toTransaction(input);
        TransactionExecutionParameter param = toParam(input);

        TransactionExecutedEvent event = transaction.execute(param);

        accountantEventPublisher.publish(event);
    }

    private TransactionService toTransaction(TransactionExecutionInput input) {
        return TransactionServiceFactory.create(input.getType());
    }

    private TransactionExecutionParameter toParam(TransactionExecutionInput input) {
        Account source = accountRepository.findById(input.getSourceId());
        Account destination = accountRepository.findById(input.getDestinationId());
        AccountPair pair = AccountPair.of(source, destination);
        return new TransactionExecutionParameter(pair, input.getAmount(), input.getDescription());
    }

}
