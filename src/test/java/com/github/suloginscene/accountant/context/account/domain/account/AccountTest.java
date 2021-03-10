package com.github.suloginscene.accountant.context.account.domain.account;

import com.github.suloginscene.accountant.context.common.value.holder.Holder;
import com.github.suloginscene.accountant.context.common.value.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.suloginscene.accountant.context.account.domain.account.AccountType.ASSET;
import static com.github.suloginscene.accountant.context.account.domain.account.AccountType.EXPENSE;
import static com.github.suloginscene.accountant.context.account.domain.account.AccountType.LIABILITY;
import static com.github.suloginscene.accountant.context.account.domain.account.AccountType.REVENUE;
import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("계정")
class AccountTest {

    Holder holder;


    @BeforeEach
    void setup() {
        holder = new Holder(1L);
    }


    @Test
    @DisplayName("자산 생성 - 자산 객체 반환")
    void createAsset_onSuccess_returnsAsset() {
        AccountCreationParameter param = createParam(ASSET, "하나 자유");
        Account asset = Account.create(param);

        assertThat(asset).isInstanceOf(Asset.class);
    }

    @Test
    @DisplayName("부채 생성 - 부채 객체 반환")
    void createLiability_onSuccess_returnsLiability() {
        AccountCreationParameter param = createParam(LIABILITY, "현대 신용");
        Account liability = Account.create(param);

        assertThat(liability).isInstanceOf(Liability.class);
    }

    @Test
    @DisplayName("수입 생성 - 수입 객체 반환")
    void createRevenue_onSuccess_returnsRevenue() {
        AccountCreationParameter param = createParam(REVENUE, "월급");
        Account revenue = Account.create(param);

        assertThat(revenue).isInstanceOf(Revenue.class);
    }

    @Test
    @DisplayName("지출 생성 - 지출 객체 반환")
    void createExpense_onSuccess_returnsExpense() {
        AccountCreationParameter param = createParam(EXPENSE, "식비");
        Account expense = Account.create(param);

        assertThat(expense).isInstanceOf(Expense.class);
    }

    private AccountCreationParameter createParam(AccountType type, String name) {
        return new AccountCreationParameter(type, holder, name, Money.of(1));
    }


    @Test
    @DisplayName("단식 거래 기록 - 상태 변경")
    void writeSingleTransaction_onSuccess_changeState() {
        Account account = Account.create(createParam(ASSET, "신한 적금"));

        Money amount = Money.of(1);
        String description = "protected 메서드이며, 하위 클래스의 public 내에서 사용됨";
        account.writeSingleTransaction(amount, description);

        assertThat(account.readSingleTransaction()).hasSize(1);
    }

    @Test
    @DisplayName("단식 거래 조회 - 사본 반환")
    void readSingleTransactions_onSuccess_returnsCloneList() {
        Account account = Account.create(createParam(ASSET, "신한 적금"));

        List<SingleTransaction> history1 = account.readSingleTransaction();
        List<SingleTransaction> history2 = account.readSingleTransaction();

        assertThat(history1).isNotSameAs(history2);
    }

}
