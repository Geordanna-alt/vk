package tests;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitUntilState;
import org.junit.jupiter.api.BeforeEach;
import pages.InventoryPage;
import pages.LoginPage;

/** База для тестов, которым нужен уже выполненный вход */
public abstract class SauceDemoBaseTest extends BaseTest {

    protected InventoryPage inventoryPage;

    @BeforeEach
    void loginAsStandardUser() {
        LoginPage loginPage = new LoginPage(page);
        loginPage.navigate();
        inventoryPage = loginPage.login("standard_user", "secret_sauce");

        page.waitForURL(
                "**/inventory.html",
                new Page.WaitForURLOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED));
    }
}
