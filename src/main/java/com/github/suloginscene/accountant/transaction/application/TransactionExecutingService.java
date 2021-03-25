package com.github.suloginscene.accountant.transaction.application;

import com.github.suloginscene.accountant.account.domain.Account;
import com.github.suloginscene.accountant.account.domain.AccountRepository;
import com.github.suloginscene.accountant.transaction.domain.AccountPair;
import com.github.suloginscene.accountant.transaction.domain.TransactionExecutedEvent;
import com.github.suloginscene.accountant.transaction.domain.TransactionExecutionParameter;
import com.github.suloginscene.accountant.transaction.domain.TransactionService;
import com.github.suloginscene.accountant.transaction.domain.TransactionServiceFactory;
import com.github.suloginscene.lib.event.EventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class TransactionExecutingService {

    private final AccountRepository accountRepository;
    private final EventPublisher eventPublisher;


    public void executeTransaction(TransactionExecutionInput input) {
        TransactionService transaction = toTransaction(input);
        TransactionExecutionParameter param = toParam(input);

        TransactionExecutedEvent event = transaction.execute(param);

        eventPublisher.publish(event);
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
