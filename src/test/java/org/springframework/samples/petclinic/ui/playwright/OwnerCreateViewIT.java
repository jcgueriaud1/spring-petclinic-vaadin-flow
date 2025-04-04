package org.springframework.samples.petclinic.ui.playwright;

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
public class OwnerCreateViewIT extends BasePlayWrightIT {

	@Override
	public String getView() {
		return "owners/new";
	}

	@Test
    public void testH2Title() {
        assertThat(page.locator("h2")).hasText("Owner");
    }

	@Test
    public void testCreateOwner() {
		fill(getTextFieldByLabel("Last Name"), "Doe");
		fill(getTextFieldByLabel("First Name"), "John");
		fill(getTextFieldByLabel("Address"), "Address");
		fill(getTextFieldByLabel("City"), "Turku");
		fill(getTextFieldByLabel("Telephone"), "044444444");

		click(getButtonByText("Add Owner"));
		assertThat(page.locator("h2")).hasText("Owner Information");
	}

	@Test
	public void testInvalidTelephone() {
		fill(getTextFieldByLabel("Last Name"), "Doe");
		fill(getTextFieldByLabel("First Name"), "John");
		fill(getTextFieldByLabel("Address"), "Address");
		fill(getTextFieldByLabel("City"), "Turku");
		Locator telephoneField = getTextFieldByLabel("Telephone");
		fill(telephoneField, "0444444444444");

		click(getButtonByText("Add Owner"));
		assertThat(telephoneField).hasAttribute("invalid", "");
		assertThat(page.locator("div").getByText("Telephone must have 1 to 10 digits")).isVisible();
	}
}
