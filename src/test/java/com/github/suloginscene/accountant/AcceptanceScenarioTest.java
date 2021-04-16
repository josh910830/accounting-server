package com.github.suloginscene.accountant;

import com.github.suloginscene.accountant.account.api.request.AccountBudgetChangeRequest;
import com.github.suloginscene.accountant.account.api.request.AccountCreationRequest;
import com.github.suloginscene.accountant.account.api.request.AccountNameChangeRequest;
import com.github.suloginscene.accountant.report.api.request.IncomeStatementRequest;
import com.github.suloginscene.accountant.transaction.api.request.TransactionExecutionRequest;
import com.github.suloginscene.jwt.JwtFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.suloginscene.test.RequestBuilder.ofDelete;
import static com.github.suloginscene.test.RequestBuilder.ofGet;
import static com.github.suloginscene.test.RequestBuilder.ofPost;
import static com.github.suloginscene.test.RequestBuilder.ofPut;
import static com.github.suloginscene.test.ResultParser.toResponseAsJsonMap;
import static com.github.suloginscene.time.DateTimeFormatters.DATE;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * 서비스 전체를 표현하기 위한 시나리오 테스트입니다.
 * 행복한 경로에 대해서만 작성되었고, 테스트 메서드들은 순차적으로 관계되어 있습니다.
 * 개별 클래스의 동작은 해당 클래스의 테스트 코드에 표현되어 있습니다.
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("인수 시나리오 테스트")
public class AcceptanceScenarioTest {

    @Autowired MockMvc mockMvc;
    @Autowired JwtFactory jwtFactory;

    // 올바른 JWT 를 가지고 있다고 가정합니다.
    String jwt;

    // 프런트엔드에서의 index._links 참조에 해당합니다.
    Map<String, String> relPathMap = new HashMap<>();

    // 테스트 코드에서 계정을 Id 대신 이름으로 표현합니다.
    Map<String, Long> nameIdMap = new HashMap<>();


    @BeforeAll
    void setJwt() {
        jwt = jwtFactory.create(1L);
    }


    @Order(1)
    @Test
    @DisplayName("인덱스 - 200: 링크 6개")
    void index() throws Exception {
        String url = "/api";

        ResultActions getIndex = mockMvc.perform(ofGet(url).build());

        MvcResult result = getIndex.andExpect(status().isOk()).andReturn();

        setRelPathMap(result);
        assertThat(relPathMap.get("postAccount")).isEqualTo("/api/accounts");
        assertThat(relPathMap.get("getAccounts")).isEqualTo("/api/accounts");
        assertThat(relPathMap.get("executeTransaction")).isEqualTo("/api/transactions");
        assertThat(relPathMap.get("getLedger")).isEqualTo("/api/reports/ledger");
        assertThat(relPathMap.get("getBalanceSheet")).isEqualTo("/api/reports/balance-sheet");
        assertThat(relPathMap.get("getIncomeStatement")).isEqualTo("/api/reports/income-statement");
    }

    private void setRelPathMap(MvcResult mvcResult) throws UnsupportedEncodingException {
        Map<String, Object> resultMap = toResponseAsJsonMap(mvcResult);
        Map<String, Object> links = (Map<String, Object>) resultMap.get("_links");
        links.forEach((rel, value) -> {
            String href = ((Map<String, String>) value).get("href");
            int startOfPath = href.indexOf("/api");
            String path = href.substring(startOfPath);
            relPathMap.put(rel, path);
        });
    }


    @Order(2)
    @Test
    @DisplayName("계정 등록: 총 7개(2/2/1/2) - 201")
    void postAccounts() throws Exception {
        String url = relPathMap.get("postAccount");

        List<AccountCreationRequest> accountCreationRequests = List.of(
                new AccountCreationRequest("ASSET", "저축 계좌", 10_000_000),
                new AccountCreationRequest("ASSET", "생활비 계좌", 1_000_000),
                new AccountCreationRequest("LIABILITY", "비상금 카드", 100_000),
                new AccountCreationRequest("LIABILITY", "김철수 채무", 0),
                new AccountCreationRequest("REVENUE", "월급", 3_000_000),
                new AccountCreationRequest("EXPENSE", "식비", 300_000),
                new AccountCreationRequest("EXPENSE", "기타", 300_000)
        );

        for (AccountCreationRequest request : accountCreationRequests) {
            mockMvc.perform(ofPost(url).jwt(jwt).json(request).build()).andExpect(status().isCreated());
        }
    }

    @Order(3)
    @Test
    @DisplayName("계정 목록 - 200: 계정정보 7개")
    void getAccounts() throws Exception {
        String url = relPathMap.get("getAccounts");

        ResultActions getAccounts = mockMvc.perform(ofGet(url).jwt(jwt).build());

        MvcResult result = getAccounts.andExpect(status().isOk()).andReturn();

        setNameIdMap(result);
        assertThat(nameIdMap.size()).isEqualTo(7);
        assertThat(nameIdMap.values()).doesNotContainNull();
    }

    private void setNameIdMap(MvcResult mvcResult) throws UnsupportedEncodingException {
        Map<String, Object> resultMap = toResponseAsJsonMap(mvcResult);
        List<Map<String, Object>> accounts = ((List<Object>) resultMap.get("accounts")).stream()
                .map(account -> (Map<String, Object>) account)
                .collect(toList());
        accounts.forEach(a -> {
            String name = a.get("name").toString();
            Long id = Long.parseLong(a.get("id").toString());
            nameIdMap.put(name, id);
        });
    }


    @Order(4)
    @Test
    @DisplayName("거래 실행: 총 6건(종류별) - 200")
    void executeTransactions() throws Exception {
        String url = relPathMap.get("executeTransaction");

        List<TransactionExecutionRequest> transactionExecutionRequests = List.of(
                new TransactionExecutionRequest("SELL",
                        nameIdMap.get("월급"), nameIdMap.get("저축 계좌"), 3_000_000, "3월 급여"),
                new TransactionExecutionRequest("PURCHASE_BY_CASH",
                        nameIdMap.get("생활비 계좌"), nameIdMap.get("식비"), 50_000, "장보기"),
                new TransactionExecutionRequest("PURCHASE_BY_CREDIT",
                        nameIdMap.get("비상금 카드"), nameIdMap.get("기타"), 20_000, "병원"),
                new TransactionExecutionRequest("BORROW",
                        nameIdMap.get("김철수 채무"), nameIdMap.get("생활비 계좌"), 1_000_000, "이사(5/1에 갚기)"),
                new TransactionExecutionRequest("REPAY",
                        nameIdMap.get("생활비 계좌"), nameIdMap.get("비상금 카드"), 100_000, "3월 카드 대금"),
                new TransactionExecutionRequest("TRANSFER",
                        nameIdMap.get("저축 계좌"), nameIdMap.get("생활비 계좌"), 600_000, "4월 생활비")
        );

        for (TransactionExecutionRequest request : transactionExecutionRequests) {
            mockMvc.perform(ofPost(url).jwt(jwt).json(request).build()).andExpect(status().isOk());
        }
    }

    @Order(5)
    @Test
    @DisplayName("계정 조회 - 200: 단식 거래기록 개수 일치")
    void getAccount() throws Exception {
        assertAccountHasTransactions("저축 계좌", 2);
        assertAccountHasTransactions("생활비 계좌", 4);
        assertAccountHasTransactions("비상금 카드", 2);
        assertAccountHasTransactions("김철수 채무", 1);
        assertAccountHasTransactions("월급", 1);
        assertAccountHasTransactions("식비", 1);
        assertAccountHasTransactions("기타", 1);
    }

    private void assertAccountHasTransactions(String name, int size) throws Exception {
        String baseUrl = relPathMap.get("getAccounts");
        String url = baseUrl + "/" + nameIdMap.get(name);

        ResultActions getAccount = mockMvc.perform(ofGet(url).jwt(jwt).build());

        MvcResult result = getAccount.andExpect(status().isOk()).andReturn();

        Map<String, Object> resultMap = toResponseAsJsonMap(result);
        List<Object> singleTransactions = (List<Object>) resultMap.get("singleTransactions");
        assertThat(singleTransactions.size()).isEqualTo(size);
    }


    @Order(6)
    @Test
    @DisplayName("계정 수정: 식비(이름&예산) - 204")
    void configureAccounts() throws Exception {
        String baseUrl = relPathMap.get("postAccount");

        String nameUrl = baseUrl + "/" + nameIdMap.get("식비") + "/name";
        ResultActions putName = mockMvc.perform(
                ofPut(nameUrl).jwt(jwt).json(new AccountNameChangeRequest("외식")).build());
        putName.andExpect(status().isNoContent());

        changeKeyOfNameIdMap("식비", "외식");

        String budgetUrl = baseUrl + "/" + nameIdMap.get("외식") + "/budget";
        ResultActions putBudget = mockMvc.perform(
                ofPut(budgetUrl).jwt(jwt).json(new AccountBudgetChangeRequest(300_000)).build());
        putBudget.andExpect(status().isNoContent());
    }

    private void changeKeyOfNameIdMap(String oldKey, String newKey) {
        Long value = nameIdMap.get(oldKey);
        nameIdMap.remove(oldKey);
        nameIdMap.put(newKey, value);
    }

    @Order(7)
    @Test
    @DisplayName("계정 삭제: 월급 - 204")
    void deleteAccount() throws Exception {
        String url = relPathMap.get("postAccount") + "/" + nameIdMap.get("월급");

        ResultActions deleteAccount = mockMvc.perform(ofDelete(url).jwt(jwt).build());

        deleteAccount.andExpect(status().isNoContent());
    }


    @Order(8)
    @Test
    @DisplayName("장부 조회 - 200: 복식 거래기록 개수 일치 / 계정 변경&삭제 영향 없음")
    void getLedger() throws Exception {
        String url = relPathMap.get("getLedger");

        ResultActions getLedger = mockMvc.perform(ofGet(url).jwt(jwt).build());

        MvcResult result = getLedger.andExpect(status().isOk()).andReturn();

        List<Object> list = (List<Object>) toResponseAsJsonMap(result).get("doubleTransactions");
        assertThat(list.size()).isEqualTo(6);

        List<Map<String, String>> doubleTransactions = list.stream().map(o -> (Map<String, String>) o).collect(toList());
        assertThat(doubleTransactions.stream().anyMatch(t -> t.get("debit").equals("식비"))).isTrue();
        assertThat(doubleTransactions.stream().anyMatch(t -> t.get("credit").equals("월급"))).isTrue();
    }

    @Order(9)
    @Test
    @DisplayName("재무상태표 조회 - 200: 잔액 일치")
    void getBalanceSheet() throws Exception {
        String url = relPathMap.get("getBalanceSheet");

        ResultActions getBalanceSheet = mockMvc.perform(ofGet(url).jwt(jwt).build());

        MvcResult result = getBalanceSheet.andExpect(status().isOk()).andReturn();

        int saving = 10_000_000 + 3_000_000 - 600_000;
        int living = 1_000_000 - 50_000 + 1_000_000 - 100_000 + 600_000;
        int asset = saving + living;

        int emergency = 100_000 + 20_000 - 100_000;
        int dept = 1_000_000;
        int liability = emergency + dept;

        int net = asset - liability;

        Map<String, Object> resultMap = toResponseAsJsonMap(result);

        Map<String, Object> total = (Map<String, Object>) resultMap.get("total");
        assertThat(total.get("net")).isEqualTo(net);
        assertThat(total.get("assetSum")).isEqualTo(asset);
        assertThat(total.get("liabilitySum")).isEqualTo(liability);

        List<Object> assets = (List<Object>) resultMap.get("assets");
        assertThat(assets.size()).isEqualTo(2);

        List<Object> liabilities = (List<Object>) resultMap.get("liabilities");
        assertThat(liabilities.size()).isEqualTo(2);
    }

    @Order(10)
    @Test
    @DisplayName("손익계산서 조회 - 200: 발생량 일치 / 삭제된 계정 미포함")
    void getIncomeStatement() throws Exception {
        String url = relPathMap.get("getIncomeStatement");

        String today = LocalDate.now().format(DATE);
        ResultActions getIncomeStatement = mockMvc.perform(
                ofGet(url).jwt(jwt).json(new IncomeStatementRequest(today, today)).build());

        MvcResult result = getIncomeStatement.andExpect(status().isOk()).andReturn();

        int revenue = 0;

        int food = 50_000;
        int etc = 20_000;
        int expense = food + etc;

        int profit = revenue - expense;

        Map<String, Object> resultMap = toResponseAsJsonMap(result);

        Map<String, Object> total = (Map<String, Object>) resultMap.get("total");
        assertThat(total.get("profit")).isEqualTo(profit);
        assertThat(total.get("revenueSum")).isEqualTo(revenue);
        assertThat(total.get("expenseSum")).isEqualTo(expense);

        List<Object> revenues = (List<Object>) resultMap.get("revenues");
        assertThat(revenues.size()).isEqualTo(0);

        List<Object> expenses = (List<Object>) resultMap.get("expenses");
        assertThat(expenses.size()).isEqualTo(2);
    }


    @Order(11)
    @Test
    @DisplayName("정리 - 204")
    void clear() throws Exception {
        String url = relPathMap.get("clear");

        ResultActions clear = mockMvc.perform(ofDelete(url).jwt(jwt).build());

        clear.andExpect(status().isNoContent());

        MvcResult accountsResult = mockMvc.perform(ofGet(relPathMap.get("getAccounts")).jwt(jwt).build()).andReturn();
        List<Object> accounts = (List<Object>) toResponseAsJsonMap(accountsResult).get("accounts");
        assertThat(accounts).hasSize(0);
    }

}
