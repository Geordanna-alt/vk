package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/** Базовый класс для тестов: настраивает Selenide и закрывает браузер. */
public abstract class BaseTest {

  @BeforeEach
  void setUp() {
    Configuration.baseUrl = "https://the-internet.herokuapp.com";
    Configuration.browser = "chrome";
    Configuration.browserSize = "1920x1080";
    Configuration.timeout = 10_000;
    Configuration.pageLoadTimeout = 120_000;
    Configuration.headless = false;
    Configuration.screenshots = true;
    Configuration.savePageSource = false;
    Configuration.pageLoadStrategy = "eager";
  }

  @AfterEach
  void tearDown() {
    Selenide.closeWebDriver();
  }
}
