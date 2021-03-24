package com.github.suloginscene.accountant;

import com.github.suloginscene.accountant.account.api.AccountRestController;
import com.github.suloginscene.accountant.report.api.ReportRestController;
import com.github.suloginscene.accountant.transaction.api.TransactionRestController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@RestController
@RequestMapping("/api")
public class RootRestController {

    private final RepresentationModel<?> body = RepresentationModel.of(null, indexLinks());

    private List<Link> indexLinks() {
        return List.of(
                linkTo(AccountRestController.class).withRel("post-account"),
                linkTo(AccountRestController.class).withRel("get-accounts"),
                linkTo(TransactionRestController.class).withRel("execute-transaction"),
                linkTo(ReportRestController.class).slash("ledger").withRel("get-ledger"),
                linkTo(ReportRestController.class).slash("balance-sheet").withRel("get-balance-sheet"),
                linkTo(ReportRestController.class).slash("income-statement").withRel("get-income-statement")
        );
    }


    @GetMapping
    ResponseEntity<RepresentationModel<?>> getIndex() {
        return ResponseEntity.ok(body);
    }

}
