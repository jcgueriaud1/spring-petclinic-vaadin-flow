package org.springframework.samples.petclinic.ui.playwright;

import java.util.List;

import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Tag("playwright")
public class OwnersFindViewIT extends BasePlayWrightIT {

    @BeforeEach
    public void setupTest() throws Exception {
        super.setupTest();
    }

	@Override
	public String getView() {
		return "owners/find";
	}

	@Test
    public void testH2Title() {
        assertThat(page.locator("h2")).hasText("Find owners");
    }
	@Test
    public void testSearchLastName() {
		Locator lastName = page.locator("vaadin-text-field").getByLabel("Last Name");
		assertThat(lastName).isVisible();
		fill(lastName, "Fr");
		List<Locator> links = page.locator("vaadin-grid a").all();
	}
}
