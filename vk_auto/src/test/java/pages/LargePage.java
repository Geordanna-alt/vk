package pages;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

/** Страница с большим количеством siblings-элементов. */
public class LargePage {
  private final ElementsCollection siblings = $$("[id^='sibling-']");

  /**
   * Возвращает элемент по номеру строки и столбца.
   *
   * @param row номер строки
   * @param col номер столбца
   * @return элемент страницы
   */
  public SelenideElement getSibling(int row, int col) {
    return $("#sibling-" + row + "\\." + col);
  }

  public ElementsCollection getAllSiblings() {
    return siblings;
  }
}
