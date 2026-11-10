package pages;

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;

/** Страница защищённой области после успешного входа. */
public class SecureAreaPage {

  private final SelenideElement flashMessage = $("#flash");
  private final SelenideElement logoutButton = $("a[href='/logout']");

  public SelenideElement getFlashMessage() {
    return flashMessage;
  }

  /**
   * Выполняет выход из защищённой области.
   *
   * @return страница авторизации
   */
  public LoginPage logout() {
    logoutButton.click();
    return new LoginPage();
  }
}
