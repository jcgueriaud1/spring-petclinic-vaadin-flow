package org.springframework.samples.petclinic.playwright;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Tag("playwright")
public class TrivialPlaywrightTest {

    // This will be injected with the random free port
    // number that was allocated
    @LocalServerPort
    private int port;

    static Playwright playwright = Playwright.create();

    @Test
    public void testClicking() {
        Browser browser = playwright.chromium().launch();
        Page page = browser.newPage();
        page.navigate("http://localhost:" + port + "/");

        assertThat(page.getByText("Welcome")).isVisible();

        assertThat(page.locator("//h1")).containsText("Welcome");

    }
}
