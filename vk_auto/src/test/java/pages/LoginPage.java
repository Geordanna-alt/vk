package pages;

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;

/** Страница авторизации. */
public class LoginPage {
  private final SelenideElement usernameInput = $("#username");
  private final SelenideElement passwordInput = $("#password");
  private final SelenideElement submitButton = $("button[type='submit']");
  private final SelenideElement flashMessage = $("#flash");

  /**
   * Выполняет вход с указанными учётными данными.
   *
   * @param username имя пользователя
   * @param password пароль
   * @return страница защищённой области
   */
  public SecureAreaPage loginAs(String username, String password) {
    usernameInput.setValue(username);
    passwordInput.setValue(password);
    submitButton.click();
    return new SecureAreaPage();
  }

  /**
   * Выполняет вход с заведомо неверными учётными данными.
   *
   * @param username имя пользователя
   * @param password пароль
   * @return эта же страница авторизации
   */
  public LoginPage loginAsInvalid(String username, String password) {
    usernameInput.setValue(username);
    passwordInput.setValue(password);
    submitButton.click();
    return this;
  }

  public SelenideElement getFlashMessage() {
    return flashMessage;
  }
}
