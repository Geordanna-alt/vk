package tests;

import com.microsoft.playwright.Page;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import pages.SignUpPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Страница регистрации")
@Tag("signup")
@Timeout(60)
class SignUpPageTest extends BaseTest {

    @Test
    @Tag("smoke")
    @DisplayName("Открывается форма создания аккаунта")
    void pageOpens() {
        SignUpPage signUpPage = new SignUpPage(page);
        signUpPage.navigate();

        assertAll(
                () -> assertEquals("Create Account – Sauce Demo", page.title()),
                () -> assertThat(page.getByRole(com.microsoft.playwright.options.AriaRole.HEADING,
                                                new Page.GetByRoleOptions()
                                                .setName("Create Account"))).isVisible(),
                () -> assertThat(signUpPage.lastNameInput()).isVisible(),
                () -> assertThat(signUpPage.lastNameInput()).isVisible(),
                () -> assertThat(signUpPage.emailInput()).isVisible(),
                () -> assertThat(signUpPage.passwordInput()).isVisible(),
                () -> assertThat(signUpPage.createButton()).isVisible());
    }
}
