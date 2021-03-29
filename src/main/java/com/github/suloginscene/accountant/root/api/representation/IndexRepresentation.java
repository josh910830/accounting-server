package com.github.suloginscene.accountant.root.api.representation;

import com.github.suloginscene.accountant.account.api.AccountRestController;
import com.github.suloginscene.accountant.report.api.ReportRestController;
import com.github.suloginscene.accountant.transaction.api.TransactionRestController;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


public class IndexRepresentation extends RepresentationModel<IndexRepresentation> {

    public static IndexRepresentation CONSTANT = new IndexRepresentation();


    private IndexRepresentation() {

        add(linkTo(AccountRestController.class).withRel("post-account"));
        add(linkTo(AccountRestController.class).withRel("get-accounts"));

        add(linkTo(TransactionRestController.class).withRel("execute-transaction"));

        add(linkTo(ReportRestController.class).slash("ledger").withRel("get-ledger"));
        add(linkTo(ReportRestController.class).slash("balance-sheet").withRel("get-balance-sheet"));
        add(linkTo(ReportRestController.class).slash("income-statement").withRel("get-income-statement"));

    }

}
