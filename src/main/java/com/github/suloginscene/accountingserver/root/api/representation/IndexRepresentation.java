package com.github.suloginscene.accountingserver.root.api.representation;

import com.github.suloginscene.accountingserver.account.api.AccountRestController;
import com.github.suloginscene.accountingserver.report.api.ReportRestController;
import com.github.suloginscene.accountingserver.root.api.RootRestController;
import com.github.suloginscene.accountingserver.transaction.api.TransactionRestController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import static com.github.suloginscene.string.HrefAssembleUtil.href;


public class IndexRepresentation extends RepresentationModel<IndexRepresentation> {

    public static IndexRepresentation CONSTANT = new IndexRepresentation();


    private IndexRepresentation() {

        add(Link.of(href(AccountRestController.PATH)).withRel("postAccount"));
        add(Link.of(href(AccountRestController.PATH)).withRel("getAccounts"));

        add(Link.of(href(TransactionRestController.PATH)).withRel("executeTransaction"));

        add(Link.of(href(ReportRestController.PATH + "/ledger")).withRel("getLedger"));
        add(Link.of(href(ReportRestController.PATH + "/balance-sheet")).withRel("getBalanceSheet"));
        add(Link.of(href(ReportRestController.PATH + "/income-statement")).withRel("getIncomeStatement"));

        add(Link.of(href(RootRestController.PATH)).withRel("clear"));

    }

}
