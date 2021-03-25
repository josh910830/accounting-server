package com.github.suloginscene.accountant.transaction.api;

import com.github.suloginscene.lib.validation.AbstractValidator;
import com.github.suloginscene.accountant.transaction.domain.TransactionType;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;

import static java.util.stream.Collectors.toSet;


@Component
public class TransactionTypeValidator extends AbstractValidator {

    private static final Set<String> TRANSACTION_TYPES;

    static {
        TransactionType[] transactionTypes = TransactionType.values();
        TRANSACTION_TYPES = Arrays.stream(transactionTypes)
                .map(Enum::name)
                .collect(toSet());
    }


    @Override
    protected Class<?> targetClass() {
        return TransactionExecutionRequest.class;
    }

    @Override
    protected String targetField() {
        return "type";
    }

    @Override
    protected String rejectedReason() {
        return "부적절한 거래 유형";
    }

    @Override
    protected boolean isValid(Object target) {
        TransactionExecutionRequest request = (TransactionExecutionRequest) target;

        String type = request.getType();

        return TRANSACTION_TYPES.contains(type);
    }

}
