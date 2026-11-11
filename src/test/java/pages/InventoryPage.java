package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

/**
 * Page Object страницы каталога
 */
public class InventoryPage {
    private final Page page;

    public InventoryPage(Page page) {
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

    public Locator cartLink() {
        return page.getByTestId("shopping-cart-link");
    }

    public Locator cartBadge() {
        return page.getByTestId("shopping-cart-badge");
    }

    public Locator item(String name) {
        return items().filter(new Locator.FilterOptions().setHasText(name));
    }

    public void addToCart(String name) {
        item(name)
                .getByRole(
                        AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Add to cart"))
                .click();
    }

    public void openItem(String name) {
        item(name)
                .getByRole(
                        AriaRole.BUTTON,
                        new Locator.GetByRoleOptions().setName("View details for " + name))
                .first()
                .click();
    }
}
