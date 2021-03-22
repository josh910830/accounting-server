package com.github.suloginscene.accountant.context.account.api;

import com.github.suloginscene.accountant.context.account.application.AccountAuthorityChecker;
import com.github.suloginscene.accountant.context.account.application.AccountConfiguringService;
import com.github.suloginscene.accountant.context.account.application.AccountCreatingService;
import com.github.suloginscene.accountant.context.account.application.AccountCreationInput;
import com.github.suloginscene.accountant.context.account.application.AccountData;
import com.github.suloginscene.accountant.context.account.application.AccountFindingService;
import com.github.suloginscene.accountant.context.account.application.AccountSimpleData;
import com.github.suloginscene.accountant.context.account.domain.account.AccountType;
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
import org.springframework.web.bind.annotation.PutMapping;
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

    private final AccountAuthorityChecker accountAuthorityChecker;

    private final AccountCreatingService accountCreatingService;
    private final AccountFindingService accountFindingService;
    private final AccountConfiguringService accountConfiguringService;

    private final AccountTypeValidator accountTypeValidator;


    @InitBinder("accountCreationRequest")
    void addAccountTypeValidatorOnCreation(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(accountTypeValidator);
    }


    @PostMapping
    ResponseEntity<Void> postAccount(@RequestBody @Valid AccountCreationRequest request,
                                     @Authenticated Long memberId) {
        AccountCreationInput input = toInput(request, memberId);

        Long id = accountCreatingService.createAccount(input);

        URI uri = UriFactory.create(this, id);
        return ResponseEntity.created(uri).build();
    }

    private AccountCreationInput toInput(AccountCreationRequest request, Long memberId) {
        Holder holder = new Holder(memberId);
        AccountType type = AccountType.valueOf(request.getType());
        String name = request.getName();
        Money money = Money.of(request.getMoney());
        return new AccountCreationInput(holder, type, name, money);
    }


    @GetMapping("/{accountId}")
    ResponseEntity<AccountData> getAccount(@PathVariable Long accountId,
                                           @Authenticated Long memberId) {
        accountAuthorityChecker.checkAuthority(accountId, memberId);

        AccountData account = accountFindingService.findAccount(accountId);

        return ResponseEntity.ok(account);
    }

    @GetMapping
    ResponseEntity<List<AccountSimpleData>> getAccounts(@Authenticated Long memberId) {
        Holder holder = new Holder(memberId);

        List<AccountSimpleData> accounts = accountFindingService.findAccounts(holder);

        return ResponseEntity.ok(accounts);
    }


    @PutMapping("/{accountId}/name")
    ResponseEntity<Void> putAccount(@PathVariable Long accountId,
                                    @RequestBody @Valid AccountNameChangeRequest request,
                                    @Authenticated Long memberId) {
        accountAuthorityChecker.checkAuthority(accountId, memberId);

        accountConfiguringService.changeName(accountId, request.getNewName());

        return ResponseEntity.noContent().build();
    }

}
