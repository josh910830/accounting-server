package com.github.suloginscene.accountingserver.transaction.application;

import com.github.suloginscene.accountingserver.account.domain.Account;
import com.github.suloginscene.accountingserver.account.domain.AccountRepository;
import com.github.suloginscene.accountingserver.common.Money;
import com.github.suloginscene.accountingserver.transaction.application.input.TransactionExecutionInput;
import com.github.suloginscene.accountingserver.transaction.domain.AccountPair;
import com.github.suloginscene.accountingserver.transaction.domain.TransactionExecutedEvent;
import com.github.suloginscene.accountingserver.transaction.domain.TransactionService;
import com.github.suloginscene.accountingserver.transaction.domain.TransactionServiceContainer;
import com.github.suloginscene.accountingserver.transaction.domain.param.TransactionExecutionParameter;
import com.github.suloginscene.event.EventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class TransactionExecutingService {

    private final TransactionServiceContainer transactionServiceContainer;
    private final AccountRepository accountRepository;
    private final EventPublisher eventPublisher;


    public void executeTransaction(TransactionExecutionInput input) {
        TransactionService transaction = transactionServiceContainer.get(input.getType());

        Account source = accountRepository.findById(input.getSourceId());
        Account destination = accountRepository.findById(input.getDestinationId());

        TransactionExecutedEvent event = transaction.execute(toParam(source, destination, input));

        eventPublisher.publish(event);
    }

    private TransactionExecutionParameter toParam(Account source, Account destination, TransactionExecutionInput input) {
        AccountPair pair = AccountPair.of(source, destination);
        Money amount = input.getAmount();
        String description = input.getDescription();
        return new TransactionExecutionParameter(pair, amount, description);
    }

}
