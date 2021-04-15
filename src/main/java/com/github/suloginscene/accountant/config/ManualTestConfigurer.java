package com.github.suloginscene.accountant.config;

import com.github.suloginscene.accountant.account.application.AccountCreatingService;
import com.github.suloginscene.accountant.account.application.input.AccountCreationInput;
import com.github.suloginscene.accountant.account.domain.AccountType;
import com.github.suloginscene.accountant.common.Holder;
import com.github.suloginscene.accountant.common.Money;
import com.github.suloginscene.accountant.transaction.application.TransactionExecutingService;
import com.github.suloginscene.accountant.transaction.application.input.TransactionExecutionInput;
import com.github.suloginscene.accountant.transaction.domain.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static com.github.suloginscene.accountant.account.domain.AccountType.ASSET;
import static com.github.suloginscene.accountant.account.domain.AccountType.EXPENSE;
import static com.github.suloginscene.accountant.account.domain.AccountType.LIABILITY;
import static com.github.suloginscene.accountant.account.domain.AccountType.REVENUE;
import static com.github.suloginscene.accountant.transaction.domain.TransactionType.PURCHASE_BY_CASH;
import static com.github.suloginscene.accountant.transaction.domain.TransactionType.PURCHASE_BY_CREDIT;
import static com.github.suloginscene.accountant.transaction.domain.TransactionType.REPAY;
import static com.github.suloginscene.accountant.transaction.domain.TransactionType.SELL;
import static com.github.suloginscene.accountant.transaction.domain.TransactionType.TRANSFER;
import static com.github.suloginscene.profile.Profiles.LOCAL;


@Component
@Profile(LOCAL)
@RequiredArgsConstructor
public class ManualTestConfigurer implements ApplicationRunner {

    private final AccountCreatingService accountCreatingService;
    private final TransactionExecutingService transactionExecutingService;

    private final Holder holder = new Holder(1L);


    @Override
    public void run(ApplicationArguments args) {
        createAccount(ASSET, "전세", 100_000_000);
        Long aSaving = createAccount(ASSET, "국민 저축", 30_000_000);
        Long aUsing = createAccount(ASSET, "하나 자유", 1_000_000);
        Long lCredit = createAccount(LIABILITY, "현대 신용", 100_000);
        Long rSalary = createAccount(REVENUE, "월급", 3_000_000);
        Long eFixed = createAccount(EXPENSE, "고정지출", 300_000);
        Long eLiving = createAccount(EXPENSE, "생활", 400_000);
        Long eCulture = createAccount(EXPENSE, "문화", 100_000);

        executeTransaction(SELL, rSalary, aSaving, 3_000_000, "첫 월급");
        executeTransaction(REPAY, aSaving, lCredit, 100_000, "신용카드 정산");
        executeTransaction(TRANSFER, aSaving, aUsing, 800_000, "예산 이체");
        executeTransaction(PURCHASE_BY_CASH, aUsing, eFixed, 150_000, "관리비");
        executeTransaction(PURCHASE_BY_CASH, aUsing, eLiving, 20_000, "치킨");
        executeTransaction(PURCHASE_BY_CREDIT, lCredit, eCulture, 50_000, "콘서트");
    }

    private void executeTransaction(TransactionType type, Long src, Long dst, Integer money, String desc) {
        TransactionExecutionInput input = new TransactionExecutionInput(type, src, dst, Money.of(money), desc);
        transactionExecutingService.executeTransaction(input);
    }

    private Long createAccount(AccountType type, String name, Integer money) {
        AccountCreationInput input = new AccountCreationInput(holder, type, name, Money.of(money));
        return accountCreatingService.createAccount(input);
    }

}
