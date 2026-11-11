package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitUntilState;

/**
 * Page Object главной страницы стенда https://sauce-demo.myshopify.com/
 */
public class HomePage {
    private static final String URL = "https://sauce-demo.myshopify.com/";

    private final Page page;

    public HomePage(Page page) {
        this.page = page;
    }

    public void navigate() {
        page.navigate(
                URL,
                new Page.NavigateOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED));
    }

    /** Поле поиска в шапке */
    public Locator searchInput() {
        return page.getByPlaceholder("Search");
    }

    /** Кнопка отправки поиска */
    public Locator searchButton() {
        return page.getByRole(
                AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Submit"));
    }

    /** Ссылка «Log In» в шапке */
    public Locator loginLink() {
        return page.getByRole(
                AriaRole.LINK, new Page.GetByRoleOptions().setName("Log In"));
    }

    /**
     * Ссылка на корзину
     */
    public Locator cartLink() {
        return page.getByRole(
                        AriaRole.LINK, new Page.GetByRoleOptions().setName("My Cart"))
                .filter(new Locator.FilterOptions().setVisible(true));
    }

    /** Карточка товара по названию*/
    public Locator productCard(String productName) {
        return page.getByRole(
                AriaRole.LINK, new Page.GetByRoleOptions().setName(productName));
    }

    /**
     * Все карточки товаров
     */
    public Locator productCards() {
        return page.locator("a[id^='product-']");
    }

    /** Названия товаров на главной */
    public Locator productNames() {
        return productCards()
                .getByRole(AriaRole.HEADING, new Locator.GetByRoleOptions().setLevel(3));
    }

    /** Цены товаров на главной */
    public Locator productPrices() {
        return productCards()
                .getByRole(AriaRole.HEADING, new Locator.GetByRoleOptions().setLevel(4));
    }

    /** Ссылка в меню или шапке по доступному имени */
    public Locator navLink(String name) {
        return page.getByRole(
                AriaRole.LINK, new Page.GetByRoleOptions().setName(name)).first();
    }

    /** Ссылка «Check Out» в шапке */
    public Locator checkoutLink() {
        return page.getByRole(
                AriaRole.LINK, new Page.GetByRoleOptions().setName("Check Out"));
    }

    /** Футер страницы */
    public Locator footer() {
        return page.getByRole(AriaRole.CONTENTINFO);
    }

    /** Сообщение пустой корзины в выдвижной панели */
    public Locator emptyCartMessage() {
        return page.getByText("Your cart is empty");
    }

    /** Ввести поисковый запрос и отправить форму*/
    public void search(String query) {
        searchInput().fill(query);
        searchInput().press("Enter");
    }
}
