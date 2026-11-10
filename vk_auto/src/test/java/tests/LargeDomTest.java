package tests;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.open;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pages.LargePage;

/** Тесты страницы с большим количеством элементов DOM. */
@Execution(ExecutionMode.SAME_THREAD)
public class LargeDomTest extends BaseTest {

  @BeforeAll
  static void openPage() {
    Configuration.baseUrl = "https://the-internet.herokuapp.com";
    Configuration.browser = "chrome";
    Configuration.browserSize = "1920x1080";
    Configuration.timeout = 10_000;
    Configuration.pageLoadTimeout = 120_000;
    Configuration.headless = false;
    Configuration.screenshots = true;
    Configuration.savePageSource = false;
    Configuration.pageLoadStrategy = "eager";
    open("/large");
  }

  @Override
  @AfterEach
  void tearDown() {}

  @AfterAll
  static void closeBrowser() {
    Selenide.closeWebDriver();
  }

  /** Проверяет, что на странице есть хотя бы один элемент из коллекции. */
  @DisplayName("Простаяа проверка на существование")
  @Test
  public void testLocatorObject() {

    LargePage page = new LargePage();
    page.getAllSiblings().shouldHave(sizeGreaterThan(0));
  }

  private static final int ROWS = 50;
  private static final int COLS = 3;

  /**
   * Проверяет видимость элемента по его строке и столбцу.
   *
   * @param row номер строки
   * @param col номер столбца
   */
  @DisplayName("Сверка корректности значений внутри гигантского DOM")
  @ParameterizedTest
  @MethodSource("provideData")
  public void assertAllClassesInDom(int row, int col) {
    new LargePage().getSibling(row, col).shouldBe(visible);
  }

  static Stream<Arguments> provideData() {
    return IntStream.rangeClosed(1, ROWS)
        .boxed()
        .flatMap(
            row -> IntStream.rangeClosed(1, COLS).mapToObj(col -> Arguments.arguments(row, col)));
  }
}
