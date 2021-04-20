package com.github.suloginscene.accountingserver.account.api.representation;

import com.github.suloginscene.accountingserver.account.api.AccountRestController;
import com.github.suloginscene.accountingserver.account.application.data.AccountSimpleData;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@Data @EqualsAndHashCode(callSuper = false)
public class AccountsRepresentation extends RepresentationModel<AccountsRepresentation> {

    private final List<AccountSimpleRepresentation> accounts;


    public AccountsRepresentation(List<AccountSimpleData> accounts) {
        this.accounts = accounts.stream()
                .map(AccountSimpleRepresentation::new)
                .collect(Collectors.toList());
    }


    @Data @EqualsAndHashCode(callSuper = false)
    static class AccountSimpleRepresentation extends RepresentationModel<AccountSimpleRepresentation> {

        private final Long id;
        private final String name;
        private final String type;


        AccountSimpleRepresentation(AccountSimpleData account) {
            id = account.getId();
            name = account.getName();
            type = account.getType();

            add(linkTo(AccountRestController.class).slash(id).withSelfRel());
        }

    }

}
