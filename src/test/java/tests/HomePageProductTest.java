package tests;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitUntilState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import pages.HomePage;

import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Главная страница: товары")
@Tag("products")
@Timeout(60)
class HomePageProductTest extends BaseTest {

    @Test
    @DisplayName("Отображаются три товара с названиями и ценами")
    void productCards() {
        HomePage homePage = new HomePage(page);
        homePage.navigate();

        assertAll(
                () -> assertEquals(3, homePage.productCards().count()),
                () ->
                        assertEquals(
                                List.of("Grey jacket", "Noir jacket", "Striped top"),
                                homePage.productNames().allInnerTexts()),
                () ->
                        assertEquals(
                                List.of("£55.00", "£60.00", "£50.00"),
                                homePage.productPrices().allInnerTexts()));
    }

    @Test
    @DisplayName("Клик по товару открывает страницу товара")
    void openProduct() {
        HomePage homePage = new HomePage(page);
        homePage.navigate();

        homePage.productCard("Grey jacket").click();
        page.waitForURL(
                "**/products/grey-jacket**",
                new Page.WaitForURLOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED));

        assertThat(page.getByRole(AriaRole.HEADING,
                new Page.GetByRoleOptions().setName("Grey jacket")).first()).isVisible();
    }
}
