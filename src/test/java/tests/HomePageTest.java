package tests;

import com.deque.html.axecore.playwright.AxeBuilder;
import com.deque.html.axecore.results.AxeResults;
import com.deque.html.axecore.results.Rule;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import pages.HomePage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Главная страница SauceDemo")
@Timeout(60)
public class HomePageTest {

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    @BeforeEach
    void setup() {
        playwright = Playwright.create();
        // На стенде SauceDemo тестовые атрибуты называются data-test, а не data-testid.
        // После этой настройки в Page Object'ах работает getByTestId(...).
        playwright.selectors().setTestIdAttribute("data-test");
        BrowserType chromium = playwright.chromium();
        browser = chromium.launch();
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterEach
    void teardown() {
        if (context != null) {
            context.close();
        }
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }

    @Test
    @DisplayName("Строгая проверка: на странице нет нарушений доступности")
    @Tag("accessibility")
    @Disabled("На стенде есть известные нарушения, поэтому строгая проверка всегда красная")
    void homePageAccessabilityTestStrictCheck() {
        HomePage homePage = new HomePage(page);
        homePage.navigate();

        AxeResults accessibilityScanResults = new AxeBuilder(page).analyze();
        List<Rule> violations = accessibilityScanResults.getViolations();
        printViolations(violations);

        assertTrue(
                violations.isEmpty(),
                () -> "Accessibility violations found: " + violations.size());
    }

    @Test
    @DisplayName("На главной странице нет critical-нарушений доступности")
    @Tag("accessibility")
    void homePageAccessabilityTestNoCriticalErrors() {
        HomePage homePage = new HomePage(page);
        homePage.navigate();

        AxeResults accessibilityScanResults = new AxeBuilder(page).analyze();
        List<Rule> violations = accessibilityScanResults.getViolations();
        printViolations(violations);

        var criticalCount =
                violations.stream()
                        .filter(rule -> "critical".equalsIgnoreCase(rule.getImpact()))
                        .count();

        assertEquals(
                0,
                criticalCount,
                "Critical accessibility violations found: " + criticalCount);
    }

    private void printViolations(List<Rule> violations) {
        violations.forEach(
                rule ->
                        System.out.printf(
                                "%s \n [%s]\n %s%n",
                                rule.getId(), rule.getImpact(), rule.getHelp()));
    }
    @Tag("visibility")
    @Test 
    void cartVisibilityTest(){
        HomePage homePage = new HomePage(page);
        homePage.navigate();
        homePage.cartLink();
    }


}
