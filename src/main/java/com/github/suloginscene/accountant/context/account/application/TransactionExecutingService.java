package com.github.suloginscene.accountant.context.account.application;

import com.github.suloginscene.accountant.context.account.domain.account.Account;
import com.github.suloginscene.accountant.context.account.domain.account.AccountRepository;
import com.github.suloginscene.accountant.context.account.domain.transaction.AccountPair;
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
        Account source = findAccount(data.getSourceId());
        Account destination = findAccount(data.getDestinationId());
        AccountPair pair = AccountPair.of(source, destination);

        TransactionService transaction = TransactionServiceFactory.create(data.getType());
        TransactionExecutionParameter param = new TransactionExecutionParameter(pair, data.getAmount(), data.getDescription());
        DoubleTransactionExecutedEvent event = transaction.execute(param);

        accountantEventPublisher.publish(event);
    }

    private Account findAccount(Long id) {
        return accountRepository.findById(id).orElseThrow();
    }

}
