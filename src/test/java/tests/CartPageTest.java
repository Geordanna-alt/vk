package tests;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitUntilState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import pages.CartPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Корзина")
@Tag("cart")
@Timeout(60)
class CartPageTest extends SauceDemoBaseTest {

    private CartPage openCart() {
        inventoryPage.cartLink().click();
        page.waitForURL(
                "**/cart.html",
                new Page.WaitForURLOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED));
        return new CartPage(page);
    }

    @Test
    @Tag("smoke")
    @DisplayName("Пустая корзина открывается")
    void emptyCart() {
        CartPage cartPage = openCart();

        assertAll(
                () -> assertThat(cartPage.title()).hasText("Your Cart"),
                () -> assertEquals(0, cartPage.items().count()),
                () -> assertThat(cartPage.checkoutButton()).isVisible());
    }

    @Test
    @DisplayName("Добавленный товар отображается в корзине")
    void addedItemAppearsInCart() {
        inventoryPage.addToCart("Sauce Labs Backpack");
        CartPage cartPage = openCart();

        assertAll(
                () -> assertEquals(1, cartPage.items().count()),
                () -> assertThat(cartPage.itemNames()).hasText("Sauce Labs Backpack"),
                () -> assertThat(cartPage.itemPrices()).hasText("$29.99"));
    }

    @Test
    @DisplayName("Товар удаляется из корзины")
    void itemCanBeRemoved() {
        inventoryPage.addToCart("Sauce Labs Backpack");
        CartPage cartPage = openCart();

        cartPage.remove("Sauce Labs Backpack");

        assertThat(cartPage.items()).hasCount(0);
    }

    @Test
    @DisplayName("Continue Shopping возвращает в каталог")
    void continueShoppingReturnsToCatalog() {
        CartPage cartPage = openCart();

        cartPage.continueShopping();
        page.waitForURL(
                "**/inventory.html",
                new Page.WaitForURLOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED));

        assertThat(inventoryPage.title()).hasText("Products");
    }
}
