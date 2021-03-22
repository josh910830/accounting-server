package com.github.suloginscene.accountant.context.account.api;

import com.github.suloginscene.accountant.context.account.application.AccountCreatingService;
import com.github.suloginscene.accountant.context.account.application.AccountCreationInput;
import com.github.suloginscene.accountant.context.account.application.AccountData;
import com.github.suloginscene.accountant.context.account.application.AccountFindingService;
import com.github.suloginscene.accountant.context.account.application.AccountSimpleData;
import com.github.suloginscene.accountant.context.account.domain.account.AccountType;
import com.github.suloginscene.accountant.context.common.exception.ForbiddenException;
import com.github.suloginscene.accountant.context.common.util.UriFactory;
import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import com.github.suloginscene.jwtconfig.Authenticated;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountRestController {

    private final AccountCreatingService accountCreatingService;
    private final AccountFindingService accountFindingService;

    private final AccountTypeValidator accountTypeValidator;


    @InitBinder
    void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(accountTypeValidator);
    }


    @PostMapping
    ResponseEntity<Void> postAccount(@Authenticated Long memberId,
                                     @Valid @RequestBody AccountCreationRequest request) {
        AccountCreationInput input = toInput(memberId, request);

        Long id = accountCreatingService.createAccount(input);

        URI uri = UriFactory.create(this, id);
        return ResponseEntity.created(uri).build();
    }

    private AccountCreationInput toInput(Long memberId, AccountCreationRequest request) {
        Holder holder = new Holder(memberId);
        AccountType type = AccountType.valueOf(request.getType());
        String name = request.getName();
        Money money = Money.of(request.getMoney());
        return new AccountCreationInput(holder, type, name, money);
    }


    @GetMapping("/{accountId}")
    ResponseEntity<AccountData> getAccount(@Authenticated Long memberId,
                                           @PathVariable Long accountId) {
        AccountData account = accountFindingService.findAccount(accountId);

        checkAuthority(account, memberId);
        return ResponseEntity.ok(account);
    }

    private void checkAuthority(AccountData account, Long memberId) throws ForbiddenException {
        if (!account.isOwnedBy(memberId)) {
            throw new ForbiddenException(memberId, account);
        }
    }

    @GetMapping
    ResponseEntity<List<AccountSimpleData>> getAccounts(@Authenticated Long memberId) {
        Holder holder = new Holder(memberId);

        List<AccountSimpleData> accounts = accountFindingService.findAccounts(holder);

        return ResponseEntity.ok(accounts);
    }

}
