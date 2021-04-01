package com.github.suloginscene.accountant.account.api.representation;

import com.github.suloginscene.accountant.account.api.AccountRestController;
import com.github.suloginscene.accountant.account.application.data.AccountData;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@Data @EqualsAndHashCode(callSuper = false)
public class AccountRepresentation extends RepresentationModel<AccountsRepresentation> {

    private final Long id;
    private final String name;
    private final String type;
    private final Integer moneyAmount;
    private final List<AccountData.SingleTransactionData> singleTransactions;


    public AccountRepresentation(AccountData account) {
        id = account.getId();
        name = account.getName();
        type = account.getType();
        moneyAmount = account.getMoneyAmount();
        singleTransactions = account.getSingleTransactions();

        add(linkTo(AccountRestController.class).slash(id).slash("name").withRel("changeName"));
        add(linkTo(AccountRestController.class).slash(id).slash("budget").withRel("changeBudget"));
        add(linkTo(AccountRestController.class).slash(id).withRel("deleteAccount"));
    }

}
