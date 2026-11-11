package tests;

import com.deque.html.axecore.playwright.AxeBuilder;
import com.deque.html.axecore.results.Rule;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitUntilState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Доступность страниц")
@Tag("accessibility")
@Timeout(120)
class SauceDemoAccessibilityTest extends SauceDemoBaseTest {

    private long criticalViolations() {
        List<Rule> violations = new AxeBuilder(page).analyze().getViolations();

        return violations.stream()
                .filter(rule -> "critical".equalsIgnoreCase(rule.getImpact()))
                .count();
    }

    @Test
    @DisplayName("В каталоге нет critical-нарушений")
    void inventoryHasNoCriticalViolations() {
        assertEquals(0L, criticalViolations());
    }

    @Test
    @DisplayName("В корзине нет critical-нарушений")
    void cartHasNoCriticalViolations() {
        inventoryPage.cartLink().click();
        page.waitForURL(
                "**/cart.html",
                new Page.WaitForURLOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED));

        assertEquals(0L, criticalViolations());
    }
}
