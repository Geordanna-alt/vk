package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitUntilState;

/** Page Object страницы регистрации магазина sauce-demo.myshopify.com. */
public class SignUpPage {
    private static final String URL = "https://sauce-demo.myshopify.com/account/register";

    private final Page page;

    public SignUpPage(Page page) {
        this.page = page;
    }

    public void navigate() {
        page.navigate(
                URL,
                new Page.NavigateOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED));
    }

    public Locator firstNameInput() {
        return page.locator("#create_customer input[name='customer[first_name]']");
    }

    public Locator lastNameInput() {
        return page.locator("#create_customer input[name='customer[last_name]']");
    }

    public Locator emailInput() {
        return page.locator("#create_customer input[name='customer[email]']");
    }

    public Locator passwordInput() {
        return page.locator("#create_customer input[name='customer[password]']");
    }

    public Locator createButton() {
        return page.getByRole(
                AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Create"));
    }

    public void fillForm(String firstName, String lastName, String email, String password) {
        firstNameInput().fill(firstName);
        lastNameInput().fill(lastName);
        emailInput().fill(email);
        passwordInput().fill(password);
    }

    public void submit() {
        createButton().click();
    }
}
