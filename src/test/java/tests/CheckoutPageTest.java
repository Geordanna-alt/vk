package tests;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitUntilState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import pages.CartPage;
import pages.CheckoutPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Оформление заказа SauceDemo")
@Tag("checkout")
@Timeout(60)
class CheckoutPageTest extends SauceDemoBaseTest {

    private CheckoutPage checkoutPage;

    @BeforeEach
    void openCheckout() {
        inventoryPage.addToCart("Sauce Labs Backpack");
        inventoryPage.cartLink().click();
        page.waitForURL(
                "**/cart.html",
                new Page.WaitForURLOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED));

        new CartPage(page).checkout();
        page.waitForURL(
                "**/checkout-step-one.html",
                new Page.WaitForURLOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED));

        checkoutPage = new CheckoutPage(page);
    }

    @Test
    @DisplayName("Пустые поля формы показывают ошибки")
    void requiredFieldsShowErrors() {
        checkoutPage.continueToOverview();
        assertThat(checkoutPage.errorMessage()).hasText("Error: First Name is required");

        checkoutPage.firstNameInput().fill("John");
        checkoutPage.continueToOverview();
        assertThat(checkoutPage.errorMessage()).hasText("Error: Last Name is required");

        checkoutPage.lastNameInput().fill("Doe");
        checkoutPage.continueToOverview();
        assertThat(checkoutPage.errorMessage()).hasText("Error: Postal Code is required");
    }

    @Test
    @DisplayName("Успешное оформление заказа")
    void successfulCheckout() {
        checkoutPage.fillInfo("John", "Doe", "12345");
        checkoutPage.continueToOverview();
        page.waitForURL(
                "**/checkout-step-two.html",
                new Page.WaitForURLOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED));

        assertAll(
                () -> assertThat(checkoutPage.title()).hasText("Checkout: Overview"),
                () -> assertThat(checkoutPage.total()).hasText("Total: $32.39"));

        checkoutPage.finish();
        page.waitForURL(
                "**/checkout-complete.html",
                new Page.WaitForURLOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED));

        assertAll(
                () -> assertThat(checkoutPage.completeHeader()).hasText("Thank you for your order!"),
                () ->
                        assertThat(checkoutPage.completeText())
                                .containsText("Your order has been dispatched"));
    }

    @Test
    @DisplayName("Cancel возвращает в корзину")
    void cancelReturnsToCart() {
        checkoutPage.cancel();
        page.waitForURL(
                "**/cart.html",
                new Page.WaitForURLOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED));

        assertThat(new CartPage(page).title()).hasText("Your Cart");
    }
}
