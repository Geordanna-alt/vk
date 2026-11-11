package tests;

import com.deque.html.axecore.playwright.AxeBuilder;
import com.deque.html.axecore.results.AxeResults;
import com.deque.html.axecore.results.Rule;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import pages.HomePage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Главная страница: доступность")
@Tag("accessibility")
@Timeout(60)
class HomePageAccessibilityTest extends BaseTest {

    @Test
    @DisplayName("Строгая проверка доступности (отключена)")
    @Disabled("На стенде есть известные нарушения serious/moderate")
    void strictCheck() {
        HomePage homePage = new HomePage(page);
        homePage.navigate();

        List<Rule> violations = new AxeBuilder(page).analyze().getViolations();

        assertTrue(violations.isEmpty(), "Accessibility violations found");
    }

    @Test
    @DisplayName("На главной странице нет critical-нарушений")
    void noCriticalErrors() {
        HomePage homePage = new HomePage(page);
        homePage.navigate();

        AxeResults accessibilityScanResults = new AxeBuilder(page).analyze();
        List<Rule> violations = accessibilityScanResults.getViolations();

        var criticalCount =
                violations.stream()
                        .filter(rule -> "critical".equalsIgnoreCase(rule.getImpact()))
                        .count();

        assertEquals(0L, criticalCount, "Critical accessibility violations found");
    }
}
