package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import pages.HomePage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Главная страница: отображение")
@Tag("smoke")
@Timeout(60)
class HomePageSmokeTest extends BaseTest {

    @Test
    @DisplayName("Страница открывается, есть поиск и основные ссылки")
    void pageOpens() {
        HomePage homePage = new HomePage(page);
        homePage.navigate();

        assertAll(
                () -> assertEquals("Sauce Demo", page.title()),
                () -> assertThat(homePage.searchInput()).isVisible(),
                () -> assertThat(homePage.searchButton()).isVisible(),
                () -> assertThat(homePage.loginLink()).isVisible());
    }
}
