package tests;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitUntilState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pages.InventoryPage;
import pages.LoginPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Страница логина")
@Tag("login")
@Timeout(60)
class LoginPageTest extends BaseTest {

    @Test
    @Tag("smoke")
    @DisplayName("Открывается форма входа")
    void pageOpens() {
        LoginPage loginPage = new LoginPage(page);
        loginPage.navigate();

        assertAll(
                () -> assertEquals("Swag Labs", page.title()),
                () -> assertThat(loginPage.usernameInput()).isVisible(),
                () -> assertThat(loginPage.passwordInput()).isVisible(),
                () -> assertThat(loginPage.loginButton()).isVisible());
    }

    @Test
    @DisplayName("Успешный вход открывает каталог")
    void successfulLogin() {
        LoginPage loginPage = new LoginPage(page);
        loginPage.navigate();

        InventoryPage inventoryPage = loginPage.login("standard_user", "secret_sauce");
        page.waitForURL(
                "**/inventory.html",
                new Page.WaitForURLOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED));

        assertThat(inventoryPage.title()).hasText("Products");
    }

    @ParameterizedTest(name = "{0}")
    @CsvSource(
            delimiter = '|',
            value = {
                "standard_user|wrong_password|Epic sadface: Username and password do not match any user in this service",
                "locked_out_user|secret_sauce|Epic sadface: Sorry, this user has been locked out."
            })
    @DisplayName("Неверные данные показывают ошибку")
    void invalidCredentialsShowError(String username, String password, String expectedError) {
        LoginPage loginPage = new LoginPage(page);
        loginPage.navigate();

        loginPage.fillCredentials(username, password);
        loginPage.submit();

        assertThat(loginPage.errorMessage()).hasText(expectedError);
    }

    @Test
    @DisplayName("Пустые поля показывают ошибку")
    void emptyCredentialsShowError() {
        LoginPage loginPage = new LoginPage(page);
        loginPage.navigate();

        loginPage.submit();

        assertThat(loginPage.errorMessage()).hasText("Epic sadface: Username is required");
    }
}
