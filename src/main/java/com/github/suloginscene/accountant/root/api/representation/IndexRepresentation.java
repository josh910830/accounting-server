package com.github.suloginscene.accountant.root.api.representation;

import com.github.suloginscene.accountant.account.api.AccountRestController;
import com.github.suloginscene.accountant.report.api.ReportRestController;
import com.github.suloginscene.accountant.root.api.RootRestController;
import com.github.suloginscene.accountant.transaction.api.TransactionRestController;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


public class IndexRepresentation extends RepresentationModel<IndexRepresentation> {

    public static IndexRepresentation CONSTANT = new IndexRepresentation();


    private IndexRepresentation() {

        add(linkTo(AccountRestController.class).withRel("postAccount"));
        add(linkTo(AccountRestController.class).withRel("getAccounts"));

        add(linkTo(TransactionRestController.class).withRel("executeTransaction"));

        add(linkTo(ReportRestController.class).slash("ledger").withRel("getLedger"));
        add(linkTo(ReportRestController.class).slash("balance-sheet").withRel("getBalanceSheet"));
        add(linkTo(ReportRestController.class).slash("income-statement").withRel("getIncomeStatement"));

        add(linkTo(RootRestController.class).withRel("clear"));

    }

}
