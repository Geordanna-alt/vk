package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

/** Page Object страницы корзины */
public class CartPage {
    private final Page page;

    public CartPage(Page page) {
        this.page = page;
    }

    public Locator title() {
        return page.getByTestId("title");
    }

    public Locator items() {
        return page.getByTestId("inventory-item");
    }

    public Locator itemNames() {
        return page.getByTestId("inventory-item-name");
    }

    public Locator itemPrices() {
        return page.getByTestId("inventory-item-price");
    }

    public Locator checkoutButton() {
        return page.getByTestId("checkout");
    }

    public Locator continueShoppingButton() {
        return page.getByTestId("continue-shopping");
    }

    /** Товар в корзине по названию */
    public Locator item(String name) {
        return items().filter(new Locator.FilterOptions().setHasText(name));
    }

    /** Удалить товар из корзины */
    public void remove(String name) {
        item(name)
                .getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Remove"))
                .click();
    }

    /** Перейти к оформлению заказа */
    public void checkout() {
        checkoutButton().click();
    }

    /** Вернуться в каталог */
    public void continueShopping() {
        continueShoppingButton().click();
    }
}
