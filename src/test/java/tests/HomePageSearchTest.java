package tests;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitUntilState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pages.HomePage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@DisplayName("Главная страница: поиск")
@Tag("search")
@Timeout(60)
class HomePageSearchTest extends BaseTest {

    @Nested
    @DisplayName("Поиск с результатами")
    class WithResults {

        @ParameterizedTest(name = "поиск \"{0}\" находит {1}")
        @CsvSource({"Noir, Noir jacket", "Grey, Grey jacket", "Striped, Striped top"})
        @DisplayName("Поиск находит нужный товар")
        void searchFindsProduct(String query, String expectedProduct) {
            HomePage homePage = new HomePage(page);
            homePage.navigate();

            homePage.search(query);
            page.waitForURL(
                    "**/search**",
                    new Page.WaitForURLOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED));

            assertThat(homePage.productCard(expectedProduct)).isVisible();
        }
    }

    @Nested
    @DisplayName("Поиск без результатов")
    class WithoutResults {

        @Test
        @DisplayName("Показывается сообщение, что ничего не найдено")
        void searchWithoutResults() {
            HomePage homePage = new HomePage(page);
            homePage.navigate();

            homePage.search("zzzz");
            page.waitForURL(
                    "**/search**",
                    new Page.WaitForURLOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED));

            assertThat(page.getByText("No results found for zzzz")).isVisible();
        }
    }
}
