package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitUntilState;

/**
 * Page Object страницы логина
 */
public class LoginPage {
    private static final String URL = "https://www.saucedemo.com/";

    private final Page page;

    public LoginPage(Page page) {
        this.page = page;
    }

    public void navigate() {
        page.navigate(
                URL,
                new Page.NavigateOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED));
    }

    public Locator usernameInput() {
        return page.getByLabel("Username");
    }

    public Locator passwordInput() {
        return page.getByLabel("Password");
    }

    public Locator loginButton() {
        return page.getByRole(
                AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login"));
    }

    public Locator errorMessage() {
        return page.getByRole(AriaRole.ALERT);
    }

    public void fillCredentials(String username, String password) {
        usernameInput().fill(username);
        passwordInput().fill(password);
    }

    public void submit() {
        loginButton().click();
    }

    public InventoryPage login(String username, String password) {
        fillCredentials(username, password);
        submit();
        return new InventoryPage(page);
    }
}
