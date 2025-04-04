package org.springframework.samples.petclinic.ui.playwright;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Locator.DispatchEventOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.samples.petclinic.ui.HasTestView;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class BasePlayWrightIT implements HasTestView {

    @LocalServerPort
    private int port;

    @Override
    public String getUrl() {
        return String.format("http://localhost:%d/", port);
    }

    String WAIT_FOR_VAADIN_SCRIPT =
    // @formatter:off
            "() => {"
            + "if (window.Vaadin && window.Vaadin.Flow && window.Vaadin.Flow.clients) {"
            + "  var clients = window.Vaadin.Flow.clients;"
            + "  for (var client in clients) {"
            + "    if (clients[client].isActive()) {"
            + "      return false;"
            + "    }"
            + "  }"
            + "  return true;"
            + "} else if (window.Vaadin && window.Vaadin.Flow && window.Vaadin.Flow.devServerIsNotLoaded) {"
            + "  return false;"
            + "} else {"
            + "  return true;"
            + "}"
            + "}";
    // @formatter:on

    protected Page page;
    protected static Playwright playwright;
    protected static Browser browser;

    @BeforeEach
    public void setupTest() throws Exception {
        page = browser.newPage();
        page.navigate(getUrl() + getView());
        page.waitForFunction(WAIT_FOR_VAADIN_SCRIPT);
        page.setDefaultNavigationTimeout(4000);
        page.setDefaultTimeout(15000);
    }

    @AfterEach
    public void cleanupTest() {
        page.close();
    }

    @AfterAll
    public static void cleanup() {
        browser.close();
        playwright.close();
    }

    @BeforeAll
    public static void setup() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new LaunchOptions()
                .setHeadless(System.getProperty("headless") == null || Boolean.getBoolean("headless")));
    }

    protected Page getPage() {
        return page;
    }

    protected void event(Locator locator, String type, Object eventInit,
            DispatchEventOptions options) {
        locator.nth(0).dispatchEvent(type, eventInit, options);
        getPage().waitForTimeout(10);
    }

    protected void event(Locator locator, String eventName) {
        locator.nth(0).dispatchEvent(eventName);
        getPage().waitForTimeout(10);
    }

    protected void click(Locator locator) {
        if (!locator.nth(0).locator("input").all().isEmpty()) {
            locator = locator.nth(0).locator("input");
        }
        locator.nth(0).click();
        getPage().waitForTimeout(10);
    }

    protected void press(Locator locator, String key) {
        locator.nth(0).press(key);
        getPage().waitForTimeout(10);
    }

    protected void fill(Locator locator, String value) {
        if (!locator.first().locator("input").all().isEmpty()) {
			System.out.println("INPUT");
            locator = locator.first().locator("input");
        } else {
			System.out.println("NOT INPUT");
		}
        locator.nth(0).fill(value);
        locator.nth(0).blur();
        getPage().waitForTimeout(10);
    }

    protected void select(Locator locator, String val) {
        fill(locator, val);
    }

	protected Locator getTextFieldByLabel(String label) {
		Locator textFieldLocator = page.locator("vaadin-text-field").getByLabel(label);
		assertThat(textFieldLocator).isVisible();
		return textFieldLocator;
	}
	protected Locator getButtonByText(String text) {
		Locator buttonLocator = page.locator("vaadin-button").getByText(text);
		assertThat(buttonLocator).isVisible();
		return buttonLocator;
	}
}
