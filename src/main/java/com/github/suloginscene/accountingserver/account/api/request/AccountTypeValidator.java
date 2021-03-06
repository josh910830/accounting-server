package com.github.suloginscene.accountingserver.account.api.request;

import com.github.suloginscene.accountingserver.account.domain.AccountType;
import com.github.suloginscene.validation.AbstractValidator;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;

import static java.util.stream.Collectors.toSet;


@Component
public class AccountTypeValidator extends AbstractValidator {

    private static final Set<String> ACCOUNT_TYPES;

    static {
        AccountType[] accountTypes = AccountType.values();
        ACCOUNT_TYPES = Arrays.stream(accountTypes)
                .map(Enum::name)
                .collect(toSet());
    }


    @Override
    protected Class<?> targetClass() {
        return AccountCreationRequest.class;
    }

    @Override
    protected String targetField() {
        return "type";
    }

    @Override
    protected String rejectMessage() {
        return "부적절한 계정 유형입니다.";
    }

    @Override
    protected boolean isValid(Object target) {
        AccountCreationRequest request = (AccountCreationRequest) target;

        String type = request.getType();

        return ACCOUNT_TYPES.contains(type);
    }

}
