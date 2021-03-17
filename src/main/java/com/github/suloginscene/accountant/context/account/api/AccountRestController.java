package com.github.suloginscene.accountant.context.account.api;

import com.github.suloginscene.accountant.context.account.application.AccountCreatingService;
import com.github.suloginscene.accountant.context.account.application.AccountCreationInput;
import com.github.suloginscene.accountant.context.account.application.AccountFindingService;
import com.github.suloginscene.accountant.context.account.domain.account.AccountType;
import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountRestController {

    private final AccountCreatingService accountCreatingService;
    private final AccountFindingService accountFindingService;


    @PostMapping
    ResponseEntity<?> createAccount(@AuthenticationPrincipal String username, // TODO sp-el
                                    @RequestBody AccountCreationRequest request) {
        AccountCreationInput input = toInput(username, request);

        Long id = accountCreatingService.createAccount(input);

        URI uri = uri(this, id);
        return ResponseEntity.created(uri).build();
    }

    private AccountCreationInput toInput(String username, AccountCreationRequest request) {
        Holder holder = new Holder(Long.parseLong(username));
        AccountType type = AccountType.valueOf(request.getType());
        String name = request.getName();
        Money money = Money.of(request.getMoney());
        return new AccountCreationInput(holder, type, name, money);
    }

    // TODO refactor to util
    private URI uri(Object controller, Object... paths) {
        Class<?> aClass = controller.getClass();
        WebMvcLinkBuilder link = linkTo(aClass);

        for (Object path : paths) {
            link = link.slash(path);
        }

        return link.toUri();
    }

}
