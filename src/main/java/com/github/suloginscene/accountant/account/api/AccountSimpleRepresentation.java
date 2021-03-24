package com.github.suloginscene.accountant.account.api;

import com.github.suloginscene.accountant.account.application.AccountSimpleData;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@Data @EqualsAndHashCode(callSuper = false)
public class AccountSimpleRepresentation extends RepresentationModel<AccountSimpleRepresentation> {

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
