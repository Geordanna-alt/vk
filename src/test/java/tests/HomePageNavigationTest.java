package tests;

import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.Timeout;
import pages.HomePage;

import java.util.Map;
import java.util.stream.Stream;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@DisplayName("Главная страница: навигация")
@Tag("navigation")
@Timeout(60)
class HomePageNavigationTest extends BaseTest {

    @TestFactory
    @DisplayName("Ссылки меню ведут на правильные URL")
    Stream<DynamicTest> menuLinks() {
        HomePage homePage = new HomePage(page);
        homePage.navigate();

        Map<String, String> links =
                Map.of(
                        "Home", "/",
                        "Catalog", "/collections/all",
                        "Blog", "/blogs/news",
                        "About Us", "/pages/about-us");

        return links.entrySet().stream()
                .map(entry -> dynamicTest(entry.getKey(), 
                                () -> {Locator link = homePage.navLink(entry.getKey());
                                        assertAll(() -> assertThat(link).isVisible(), 
                                        () ->assertEquals( entry.getValue(), link.getAttribute("href")));
                                        }));
    }

    @Test
    @DisplayName("Log In и Sign up ведут на страницы аккаунта")
    void accountLinks() {
        HomePage homePage = new HomePage(page);
        homePage.navigate();

        assertAll(
                () ->
                        assertEquals(
                                "/account/login", homePage.loginLink().getAttribute("href")),
                () ->
                        assertEquals(
                                "/account/register",
                                homePage.navLink("Sign up").getAttribute("href")));
    }

    @Test
    @DisplayName("Check Out ведёт в корзину")
    void checkoutLink() {
        HomePage homePage = new HomePage(page);
        homePage.navigate();

        assertEquals("/cart", homePage.checkoutLink().getAttribute("href"));
    }
}
