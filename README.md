# Автотесты SauceDemo

UI-автотесты для SauceDemo, написанные на **Playwright** с использованием
**Java**, **JUnit 5** и **axe-core**.

Покрыты главная страница и регистрация на `https://sauce-demo.myshopify.com/`,
а также логин, каталог, корзина и оформление заказа на
`https://www.saucedemo.com/`.

## Требования

- JDK 25
- Maven 3.8+
- Linux/macOS/Windows (для Linux могут понадобиться системные зависимости Playwright)

## Установка

1. Установить браузеры Playwright (достаточно один раз):

   ```bash
   mvn exec:java -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install chromium"
   ```

   Чтобы установить все поддерживаемые браузеры, выполните:

   ```bash
   mvn exec:java -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install"
   ```

   Для Linux при необходимости установите системные зависимости:

   ```bash
   sudo env "PATH=$PATH" mvn exec:java -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install-deps chromium"
   ```
## Стенды и тестовые пользователи

- `https://sauce-demo.myshopify.com/` — главная страница и регистрация.
- `https://www.saucedemo.com/` — логин, каталог, корзина и checkout.
  - `standard_user` / `secret_sauce` — успешный вход.
  - `locked_out_user` / `secret_sauce` — проверка заблокированного пользователя.

## Запуск тестов

Все тесты:

```bash
mvn test
```

Один тестовый класс:

```bash
mvn test -Dtest=LoginPageTest
mvn test -Dtest=InventoryPageTest
```

Один тестовый метод:

```bash
mvn test -Dtest=LoginPageTest#successfulLogin
```

Все тесты пакета:

```bash
mvn test -Dtest="tests.*Test"
```

По тегу:

```bash
mvn test -Dgroups=smoke
mvn test -Dgroups=login
mvn test -Dgroups=inventory
mvn test -Dgroups=cart
mvn test -Dgroups=checkout
mvn test -Dgroups=accessibility
```
## Отчёты и проверки качества кода

- Результаты тестов: `target/surefire-reports/`
- Форматирование кода: `mvn spotless:apply`
- Проверка стиля: `mvn checkstyle:check`