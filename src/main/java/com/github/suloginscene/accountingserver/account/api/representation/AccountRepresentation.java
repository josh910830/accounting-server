package com.github.suloginscene.accountingserver.account.api.representation;

import com.github.suloginscene.accountingserver.account.api.AccountRestController;
import com.github.suloginscene.accountingserver.account.application.data.AccountData;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

import static com.github.suloginscene.string.HrefAssembleUtil.href;


@Data @EqualsAndHashCode(callSuper = false)
public class AccountRepresentation extends RepresentationModel<AccountRepresentation> {

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

        add(Link.of(href(AccountRestController.PATH + "/" + id + "/name")).withRel("changeName"));
        add(Link.of(href(AccountRestController.PATH + "/" + id + "/budget")).withRel("changeBudget"));
        add(Link.of(href(AccountRestController.PATH + "/" + id)).withRel("deleteAccount"));
    }

}
