package com.github.suloginscene.accountingserver.transaction.api;

import com.github.suloginscene.accountingserver.account.application.AccountAuthorityChecker;
import com.github.suloginscene.accountingserver.common.Money;
import com.github.suloginscene.accountingserver.transaction.api.request.TransactionExecutionRequest;
import com.github.suloginscene.accountingserver.transaction.api.request.TransactionTypeValidator;
import com.github.suloginscene.accountingserver.transaction.application.TransactionExecutingService;
import com.github.suloginscene.accountingserver.transaction.application.input.TransactionExecutionInput;
import com.github.suloginscene.accountingserver.transaction.domain.TransactionType;
import com.github.suloginscene.security.Authenticated;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionRestController {

    public static final String PATH = "/api/transactions";

    private final AccountAuthorityChecker accountAuthorityChecker;

    private final TransactionExecutingService transactionExecutingService;

    private final TransactionTypeValidator transactionTypeValidator;


    @InitBinder("transactionExecutionRequest")
    void addAccountTypeValidatorOnCreation(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(transactionTypeValidator);
    }


    @PostMapping
    ResponseEntity<Void> executeTransaction(@RequestBody @Valid TransactionExecutionRequest request,
                                            @Authenticated Long memberId) {
        accountAuthorityChecker.checkAuthority(request.getSourceId(), memberId);
        accountAuthorityChecker.checkAuthority(request.getDestinationId(), memberId);

        TransactionExecutionInput input = toInput(request);
        transactionExecutingService.executeTransaction(input);

        return ResponseEntity.ok().build();
    }

    private TransactionExecutionInput toInput(TransactionExecutionRequest request) {
        TransactionType type = TransactionType.valueOf(request.getType());
        Long sourceId = request.getSourceId();
        Long destinationId = request.getDestinationId();
        Money amount = Money.of(request.getAmount());
        String description = request.getDescription();
        return new TransactionExecutionInput(type, sourceId, destinationId, amount, description);
    }

}
