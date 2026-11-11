package tests;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import pages.HomePage;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Главная страница: футер")
@Tag("footer")
@Timeout(60)
class HomePageFooterTest extends BaseTest {

    @Test
    @DisplayName("Есть About Us, копирайт и иконки оплаты")
    void footerContent() {
        HomePage homePage = new HomePage(page);
        homePage.navigate();

        assertAll(
                () -> assertThat(homePage.footer()
                                .getByRole(AriaRole.HEADING,new Locator.GetByRoleOptions().setName("About Us")))
                                .isVisible(),
                () -> assertThat(homePage.footer()
                                .getByText(Pattern.compile("Copyright © \\d{4} Sauce Demo")).first())
                                .isVisible(),
                () -> assertThat(homePage.footer()
                                .getByAltText("We accept Visa")).isVisible()
                        );
    }
}
