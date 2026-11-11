package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import pages.HomePage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@DisplayName("Главная страница: корзина")
@Tag("cart")
@Timeout(60)
class HomePageCartTest extends BaseTest {

    @Test
    @DisplayName("Пустая корзина показывает сообщение")
    void emptyCart() {
        HomePage homePage = new HomePage(page);
        homePage.navigate();

        homePage.cartLink().click();

        assertThat(homePage.emptyCartMessage()).isVisible();
    }
}
