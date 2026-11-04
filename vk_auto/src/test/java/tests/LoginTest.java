package tests;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import pages.SecureAreaPage;

class LoginTest extends BaseTest {

  @Test
  @DisplayName("Успешный логин с валидными данными")
  void successfulLogin() {
    open("/login");
    SecureAreaPage securePage = new LoginPage().loginAs("tomsmith", "SuperSecretPassword!");
    securePage.getFlashMessage().shouldHave(text("Secure Area"));
  }

  @Test
  @DisplayName("Логин с невалидными данными")
  void unsuccessfulLogin() {
    open("/login");
    new LoginPage().loginAsInvalid("freak", "freak!");
    $("#flash").shouldBe(visible);
    $("#flash").shouldHave(attribute("class", "flash error"));
  }
  
}
