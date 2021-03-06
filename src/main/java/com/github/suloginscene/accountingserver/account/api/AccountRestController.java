package com.github.suloginscene.accountingserver.account.api;

import com.github.suloginscene.accountingserver.account.api.representation.AccountRepresentation;
import com.github.suloginscene.accountingserver.account.api.representation.AccountsRepresentation;
import com.github.suloginscene.accountingserver.account.api.request.AccountBudgetChangeRequest;
import com.github.suloginscene.accountingserver.account.api.request.AccountCreationRequest;
import com.github.suloginscene.accountingserver.account.api.request.AccountNameChangeRequest;
import com.github.suloginscene.accountingserver.account.api.request.AccountTypeValidator;
import com.github.suloginscene.accountingserver.account.application.AccountAuthorityChecker;
import com.github.suloginscene.accountingserver.account.application.AccountConfiguringService;
import com.github.suloginscene.accountingserver.account.application.AccountCreatingService;
import com.github.suloginscene.accountingserver.account.application.AccountFindingService;
import com.github.suloginscene.accountingserver.account.application.data.AccountData;
import com.github.suloginscene.accountingserver.account.application.data.AccountSimpleData;
import com.github.suloginscene.accountingserver.account.application.input.AccountCreationInput;
import com.github.suloginscene.accountingserver.account.domain.AccountType;
import com.github.suloginscene.accountingserver.common.Holder;
import com.github.suloginscene.accountingserver.common.Money;
import com.github.suloginscene.security.Authenticated;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.github.suloginscene.string.HrefAssembleUtil.href;


@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountRestController {

    public static final String PATH = "/api/accounts";

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

        URI uri = Link.of(href(PATH + "/" + id)).toUri();
        return ResponseEntity.created(uri).build();
    }

    private AccountCreationInput toInput(AccountCreationRequest request, Long memberId) {
        Holder holder = new Holder(memberId);
        AccountType type = AccountType.valueOf(request.getType());
        String name = request.getName();
        Money money = Money.of(request.getMoney());
        return new AccountCreationInput(holder, type, name, money);
    }


    @GetMapping
    ResponseEntity<AccountsRepresentation> getAccounts(@Authenticated Long memberId) {
        Holder holder = new Holder(memberId);
        List<AccountSimpleData> accounts = accountFindingService.findAccounts(holder);

        AccountsRepresentation accountsRepresentation = new AccountsRepresentation(accounts);
        return ResponseEntity.ok(accountsRepresentation);
    }

    @GetMapping("/{accountId}")
    ResponseEntity<AccountRepresentation> getAccount(@PathVariable Long accountId,
                                                     @Authenticated Long memberId) {
        accountAuthorityChecker.checkAuthority(accountId, memberId);

        AccountData account = accountFindingService.findAccount(accountId);

        AccountRepresentation accountRepresentation = new AccountRepresentation(account);
        return ResponseEntity.ok(accountRepresentation);
    }


    @PutMapping("/{accountId}/name")
    ResponseEntity<Void> putAccountName(@PathVariable Long accountId,
                                        @RequestBody @Valid AccountNameChangeRequest request,
                                        @Authenticated Long memberId) {
        accountAuthorityChecker.checkAuthority(accountId, memberId);

        String newName = request.getNewName();
        accountConfiguringService.changeName(accountId, newName);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{accountId}/budget")
    ResponseEntity<Void> putAccountBudget(@PathVariable Long accountId,
                                          @RequestBody @Valid AccountBudgetChangeRequest request,
                                          @Authenticated Long memberId) {
        accountAuthorityChecker.checkAuthority(accountId, memberId);

        Money newBudget = Money.of(request.getNewBudget());
        accountConfiguringService.changeBudget(accountId, newBudget);

        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{accountId}")
    ResponseEntity<Void> deleteAccount(@PathVariable Long accountId,
                                       @Authenticated Long memberId) {
        accountAuthorityChecker.checkAuthority(accountId, memberId);

        accountConfiguringService.delete(accountId);

        return ResponseEntity.noContent().build();
    }

}
