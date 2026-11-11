package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/** Page Object оформления заказа */
public class CheckoutPage {
    private final Page page;

    public CheckoutPage(Page page) {
        this.page = page;
    }

    public Locator title() {
        return page.getByTestId("title");
    }

    public Locator firstNameInput() {
        return page.getByTestId("firstName");
    }

    public Locator lastNameInput() {
        return page.getByTestId("lastName");
    }

    public Locator postalCodeInput() {
        return page.getByTestId("postalCode");
    }

    public Locator continueButton() {
        return page.getByTestId("continue");
    }

    public Locator cancelButton() {
        return page.getByTestId("cancel");
    }

    public Locator errorMessage() {
        return page.getByTestId("error");
    }

    public Locator finishButton() {
        return page.getByTestId("finish");
    }

    public Locator total() {
        return page.getByTestId("total-label");
    }

    public Locator completeHeader() {
        return page.getByTestId("complete-header");
    }

    public Locator completeText() {
        return page.getByTestId("complete-text");
    }

    public Locator backHomeButton() {
        return page.getByTestId("back-to-products");
    }

    public void fillInfo(String firstName, String lastName, String postalCode) {
        firstNameInput().fill(firstName);
        lastNameInput().fill(lastName);
        postalCodeInput().fill(postalCode);
    }

    public void continueToOverview() {
        continueButton().click();
    }

    public void finish() {
        finishButton().click();
    }

    public void cancel() {
        cancelButton().click();
    }
}
